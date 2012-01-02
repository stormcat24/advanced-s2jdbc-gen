/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.util;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author a.yamada
 *
 */
public class ConnectionUtil {

    /**
     * 
     */
    private ConnectionUtil() {
        throw new AssertionError();
    }
    
    public static PreparedStatement getPreparedStatement(Connection connection, String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
