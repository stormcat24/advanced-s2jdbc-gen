/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.factory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.seasar.util.sql.ResultSetUtil;

import com.google.common.collect.ImmutableList;

import me.stormcat.maven.plugin.s2jdbcgen.DelFlag;
import me.stormcat.maven.plugin.s2jdbcgen.MetaBuilder;
import me.stormcat.maven.plugin.s2jdbcgen.meta.Column;

/**
 * 
 * @author a.yamada
 *
 */
public class ColumnListBuilder implements MetaBuilder<List<Column>> {

    private List<Column> columnList;
    
    private final ResultSet resultSet;
    
    private final Set<String> primaryKeySet;
    
    private DelFlag delFlag;
    
    public ColumnListBuilder(ResultSet resultSet, Set<String> primaryKeySet) {
        this.columnList = new ArrayList<Column>();
        this.resultSet = resultSet;
        this.primaryKeySet = primaryKeySet;
    }
    
    /**
     * ${inheritDoc}
     */
    @Override
    public List<Column> build() {
        while (ResultSetUtil.next(resultSet)) {
            columnList.add(new Column(resultSet, primaryKeySet));
        }
        
        return ImmutableList.copyOf(columnList);
    }

    public DelFlag getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(DelFlag delFlag) {
        this.delFlag = delFlag;
    }
    
}
