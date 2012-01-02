/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author a.yamada
 *
 */
public class Table {

    private final String name;
    
    private final String comment;
    
    private final List<Column> columnList;
    
    private final List<Index> indexList;
    
    public Table(String name, String comment, List<Column> columnList, Map<String, Index> indexMap) {
        this.name = name;
        this.comment = comment;
        this.columnList = columnList;
        indexList = new ArrayList<Index>();
        for (Entry<String, Index> entry : indexMap.entrySet()) {
            indexList.add(entry.getValue());
        }
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public List<Index> getIndexList() {
        return indexList;
    }

}
