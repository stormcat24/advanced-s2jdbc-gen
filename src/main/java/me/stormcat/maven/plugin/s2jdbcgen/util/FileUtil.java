/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen.util;

import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * @author a.yamada
 *
 */
public class FileUtil {

    private FileUtil() {
        throw new AssertionError();
    }
    
    public static boolean exists(String path) {
        return new File(path).exists();
    }
    
    public static boolean mkdirsIfNotExists(String path) {
        File dir = new File(path);
        boolean result = dir.exists();
        if (!result) {
            return dir.mkdirs();
        }
        return result;
    }
    
    public static void writeStringToFile(File file, String data, String encoding) {
        try {
            FileUtils.writeStringToFile(file, data, encoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
