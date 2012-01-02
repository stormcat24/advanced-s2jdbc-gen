/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen;

import me.stormcat.maven.plugin.s2jdbcgen.meta.Table;

import org.seasar.util.lang.StringUtil;

/**
 * @author a.yamada
 *
 */
public class ModelMeta {

    private final Table table;
    
    private final String entityName;
    
    private final String abstractEntityName;
    
    private final String serviceName;
    
    private final String abstractServiceName;
    
    private final String namesName;
    
    public ModelMeta(Table table) {
        this.table = table;
        this.entityName = StringUtil.camelize(table.getName());
        this.abstractEntityName = String.format("Abstract%s", entityName);
        this.serviceName = String.format("%sService", this.entityName);
        this.abstractServiceName = String.format("Abstract%sService", this.entityName);
        this.namesName = String.format("%sNames", entityName);
    }

    public Table getTable() {
        return table;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getAbstractEntityName() {
        return abstractEntityName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getAbstractServiceName() {
        return abstractServiceName;
    }

    public String getNamesName() {
        return namesName;
    }
    
}
