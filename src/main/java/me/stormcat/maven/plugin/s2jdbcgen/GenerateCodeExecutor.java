package me.stormcat.maven.plugin.s2jdbcgen;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import me.stormcat.maven.plugin.s2jdbcgen.factory.ColumnListBuilder;
import me.stormcat.maven.plugin.s2jdbcgen.meta.Column;
import me.stormcat.maven.plugin.s2jdbcgen.meta.Index;
import me.stormcat.maven.plugin.s2jdbcgen.meta.Table;
import me.stormcat.maven.plugin.s2jdbcgen.util.ConnectionUtil;
import me.stormcat.maven.plugin.s2jdbcgen.util.DriverManagerUtil;
import me.stormcat.maven.plugin.s2jdbcgen.util.FileUtil;
import me.stormcat.maven.plugin.s2jdbcgen.util.VelocityUtil;

import org.seasar.util.sql.PreparedStatementUtil;
import org.seasar.util.sql.ResultSetUtil;
import org.seasar.util.sql.StatementUtil;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


public class GenerateCodeExecutor {
    
    private static final String SHOW_TABLES = "SHOW TABLES";
    
    private static final String GEN_DIR = "/develop/workspace_java/advanced-s2jdbc-gen/target/gen";
    
    private static final String ROOT_PACKAGE = "me.stormcat.sample";
    
    public void execute() {
        String schema = "iroots";
        String tableNameColumn = String.format("Tables_in_%s", schema);
        
        DriverManagerUtil.registerDriver("com.mysql.jdbc.Driver");
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        
        Map<String, ModelMeta> metaMap = new LinkedHashMap<String, ModelMeta>();
        try {
            connection = DriverManagerUtil.getConnection(String.format("jdbc:mysql://localhost:3306/%s", schema), "root", "root");
            ps = ConnectionUtil.getPreparedStatement(connection, SHOW_TABLES);        
            DatabaseMetaData metaData = connection.getMetaData();
            
            resultSet = PreparedStatementUtil.executeQuery(ps);
            while (ResultSetUtil.next(resultSet)) {
                String tableName = resultSet.getString(tableNameColumn);
                Table table = metaProcess(metaData, schema, tableName);
                metaMap.put(tableName, new ModelMeta(table));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ResultSetUtil.close(resultSet);
            StatementUtil.close(ps);
        }
        
        // generate code
        String rootPath = String.format("%s/%s", GEN_DIR, ROOT_PACKAGE.replace(".", "/"));
        String entityPath = String.format("%s/%s", rootPath, "entity");
        String servicePath = String.format("%s/%s", rootPath, "service");
        FileUtil.mkdirsIfNotExists(rootPath);
        FileUtil.mkdirsIfNotExists(entityPath);
        FileUtil.mkdirsIfNotExists(servicePath);
        
        String entityPackage = String.format("%s.entity", ROOT_PACKAGE);
        String servicePackage = String.format("%s.service", ROOT_PACKAGE);
        
        for (Entry<String, ModelMeta> entry : metaMap.entrySet()) {
            ModelMeta modelMeta = entry.getValue();
            
            String abstractEntityFilePath = String.format("%s/%s.java", entityPath, modelMeta.getAbstractEntityName());
            String entityFilePath = String.format("%s/%s.java", entityPath, modelMeta.getEntityName());
            String namesFilePath = String.format("%s/%s.java", entityPath, modelMeta.getNamesName());
            String abstractServiceFilePath = String.format("%s/%s.java", servicePath, modelMeta.getAbstractServiceName());
            String serviceFilePath = String.format("%s/%s.java", servicePath, modelMeta.getServiceName());
            
            File abstractEntityFile = new File(abstractEntityFilePath);
            File entityFile = new File(entityFilePath);
            File namesFile = new File(namesFilePath);
            File abstractServiceFile = new File(abstractServiceFilePath);
            File serviceFile = new File(serviceFilePath);
            
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("entityPackage", entityPackage);
            params.put("servicePackage", servicePackage);
            params.put("meta", modelMeta);
            
            writeContentsToFile(abstractEntityFile, "template/abstract_entity.vm", params, true);
            writeContentsToFile(entityFile, "template/entity.vm", params, false);
            writeContentsToFile(namesFile, "template/names.vm", params, true);
            writeContentsToFile(abstractServiceFile, "template/abstract_service.vm", params, true);
            writeContentsToFile(serviceFile, "template/service.vm", params, false);
        }
        
    }
    
    private Table metaProcess(DatabaseMetaData metaData, String schema, String tableName) throws Exception {
        try {
            Set<String> primaryKeySet = getPrimaryKeySet(metaData, schema, tableName);
            ColumnListBuilder columnListBuilder = new ColumnListBuilder(metaData.getColumns(null, schema, tableName, "%"), primaryKeySet);
            List<Column> columnList = columnListBuilder.build();

            ResultSet rs = metaData.getIndexInfo("", schema, tableName, false, false);
            
            Map<String, Index> indexMap = new LinkedHashMap<String, Index>();
            while (rs.next()) {
                String indexName = rs.getString("INDEX_NAME");
                if (!"PRIMARY".equals(indexName)) {
                    boolean unique = !rs.getBoolean("NON_UNIQUE");
                    Index index = indexMap.containsKey(indexName) ? indexMap.get(indexName) : new Index(indexName, unique);
                    final String columnName = rs.getString("COLUMN_NAME");
                    Collection<Column> target = Collections2.filter(columnList, new Predicate<Column>() {
                        @Override
                        public boolean apply(Column input) {
                            return input.getColumnName().equals(columnName);
                        }
                    });
                    if (!target.isEmpty()) {
                        index.addColumn(target.iterator().next(), rs.getInt("ORDINAL_POSITION"), rs.getString("ASC_OR_DESC").equals("A"));    
                    }
                    
                    indexMap.put(indexName, index);
                }
            }
            rs.close();             

            return new Table(tableName, "", columnList, indexMap);
        } catch (Exception e) {
            throw e;
        }
    }
    
    private Set<String> getPrimaryKeySet(DatabaseMetaData metaData, String schema, String tableName) throws Exception {
        Set<String> columnSet = new LinkedHashSet<String>();
        ResultSet rs = metaData.getPrimaryKeys(null, schema, tableName);
        while (rs.next()) {
            columnSet.add(rs.getString("COLUMN_NAME"));
        }
        rs.close();
        return columnSet;
    }
    
    private void writeContentsToFile(File file, String template, Map<String, Object> params, boolean overwrite) {
        if (overwrite || !file.exists()) {
            String entityContents = VelocityUtil.merge(template, params);
            FileUtil.writeStringToFile(file, entityContents, "UTF-8");
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new GenerateCodeExecutor().execute();
    }

}
