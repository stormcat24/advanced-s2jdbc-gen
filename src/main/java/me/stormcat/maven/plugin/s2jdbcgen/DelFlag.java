/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen;

/**
 * @author a.yamada
 *
 */
public class DelFlag {

    private String name;
    
    private boolean delValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDelValue() {
        return delValue;
    }

    public void setDelValue(boolean delValue) {
        this.delValue = delValue;
    }
    
}
