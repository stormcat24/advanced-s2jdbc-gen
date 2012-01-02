/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.meta;

/**
 * @author a.yamada
 *
 */
public class IndexColumn {

    private final Column referencedColumn;
    
    private final int ordinalPosition;
    
    private final boolean asc;

    public IndexColumn(Column referencedColumn, int ordinalPosition, boolean asc) {
        this.referencedColumn = referencedColumn;
        this.ordinalPosition = ordinalPosition;
        this.asc = asc;
    }

    public Column getReferencedColumn() {
        return referencedColumn;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public boolean isAsc() {
        return asc;
    }
    
}
