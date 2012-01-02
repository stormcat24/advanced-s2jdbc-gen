/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.util;

/**
 * @author a.yamada
 *
 */
public class StringUtil extends org.seasar.util.lang.StringUtil {

    public static String camelizeMethod(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        final String[] array = StringUtil.split(s, "_");
        if (array.length == 1) {
            return s;
        }
        final StringBuilder buf = new StringBuilder(40);
        buf.append(array[0]);
        for (int i = 1; i < array.length; ++i) {
            buf.append(StringUtil.capitalize(array[i]));
        }
        return buf.toString();
    }
    
    public static String generateReturnCode(int spaceCount) {
        StringBuilder builder = new StringBuilder("\n");
        for (int i = 0; i < spaceCount; i++) {
            builder.append(" ");    
        }
        return builder.toString();
    }
}
