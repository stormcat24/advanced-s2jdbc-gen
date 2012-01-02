/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.meta;

import java.util.ArrayList;
import java.util.List;

import me.stormcat.maven.plugin.s2jdbcgen.util.StringUtil;

/**
 * @author a.yamada
 *
 */
public class Index {

    private final String name;
    
    private final List<IndexColumn> columnList;
    
    private final boolean unique;
    
    public Index(String name, boolean unique) {
        this.name = name;
        this.unique = unique;
        this.columnList = new ArrayList<IndexColumn>();
    }
    
    public void addColumn(Column referencedColumn, int ordinalPosition, boolean asc) {
        columnList.add(new IndexColumn(referencedColumn, ordinalPosition, asc));
    }

    public String getName() {
        return name;
    }

    public List<IndexColumn> getColumnList() {
        return columnList;
    }

    public boolean isUnique() {
        return unique;
    }

    public String getFindMethodName() {
        StringBuilder builder = new StringBuilder("findBy");
        for (int i = 0; i < columnList.size(); i++) {
            IndexColumn indexColumn = columnList.get(i);
            String s = indexColumn.getReferencedColumn().getFieldName();
            if (i == 0) {
                builder.append(s.substring(0, 1).toUpperCase());
                builder.append(s.substring(1, s.length()));
            } else {
                builder.append(s);
            }
            if (i < columnList.size() - 1) {
                builder.append("And");
            }
        }
        
        return builder.toString();
    }
    
    public String getWhereClause() {
        if (columnList.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int size = columnList.size();
        int spaceCount = size > 1 ? 20 : 16;
        if (size > 1) {
            builder.append("and(");
            builder.append(StringUtil.generateReturnCode(20));
        }
        for (int i = 0; i < size; i++) {
            IndexColumn indexColumn = columnList.get(i);
            Column column = indexColumn.getReferencedColumn();
            builder.append("eq(");
            builder.append(column.getFieldName());
            builder.append("(), ");
            builder.append(column.getFieldName());
            builder.append(")");
            if (i < size - 1) {
                builder.append(", ");
                builder.append(StringUtil.generateReturnCode(spaceCount));
            }
        }
        if (size > 1) {
            builder.append(StringUtil.generateReturnCode(16));
            builder.append(")");
        }
        return builder.toString();
    }
    
    public String getSignature() {
        if (columnList.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int size = columnList.size();
        for (int i = 0; i < size; i++) {
            IndexColumn indexColumn = columnList.get(i);
            Column column = indexColumn.getReferencedColumn();
            builder.append(column.getJavaType());
            builder.append(" ");
            builder.append(column.getFieldName());
            if (i < size - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
    
}
