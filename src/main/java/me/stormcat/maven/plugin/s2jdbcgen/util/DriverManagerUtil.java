/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author a.yamada
 *
 */
public class DriverManagerUtil extends org.seasar.util.sql.DriverManagerUtil {
    
    private DriverManagerUtil() {
        throw new AssertionError();
    }

    public static Connection getConnection(String url, String user, String password) {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
