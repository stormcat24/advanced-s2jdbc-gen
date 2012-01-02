/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.seasar.util.io.ResourceUtil;

/**
 * @author a.yamada
 *
 */
public class VelocityUtil {
    
    static {
        Velocity.init(ResourceUtil.getProperties("velocity.properties"));
    }

    private VelocityUtil() {
        throw new AssertionError();
    }
    
    
    public static String merge(String templateName, Map<String, Object> params) {
        VelocityContext context = new VelocityContext();
        for (Entry<String, Object> entry : params.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        Template template = Velocity.getTemplate(templateName);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw.toString();
    }
}
