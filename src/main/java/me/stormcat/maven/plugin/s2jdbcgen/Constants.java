/**
 * 
 */
package me.stormcat.maven.plugin.s2jdbcgen;

/**
 * @author a.yamada
 *
 */
public class Constants {

    public static class ColumnMetaColumn {
        public static final String TABLE_CAT = "TABLE_CAT";
        public static final String TABLE_SCHEM = "TABLE_SCHEM";
        public static final String TABLE_NAME = "TABLE_NAME";
        public static final String COLUMN_NAME = "COLUMN_NAME";
        public static final String DATA_TYPE = "DATA_TYPE";
        public static final String TYPE_NAME = "TYPE_NAME";
        public static final String COLUMN_SIZE = "COLUMN_SIZE";
        public static final String BUFFER_LENGTH = "BUFFER_LENGTH";
        public static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
        public static final String NUM_PREC_RADIX = "NUM_PREC_RADIX";
        public static final String NULLABLE = "NULLABLE";
        public static final String REMARKS = "REMARKS";
        public static final String COLUMN_DEF = "COLUMN_DEF";
        public static final String SQL_DATA_TYPE = "SQL_DATA_TYPE";
        public static final String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";
        public static final String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";
        public static final String ORDINAL_POSITION = "ORDINAL_POSITION";
        public static final String IS_NULLABLE = "IS_NULLABLE";
        public static final String SCOPE_CATALOG = "SCOPE_CATALOG";
        public static final String SCOPE_SCHEMA = "SCOPE_SCHEMA";
        public static final String SCOPE_TABLE = "SCOPE_TABLE";
        public static final String SOURCE_DATA_TYPE = "SOURCE_DATA_TYPE";
        public static final String IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
    }
    
    public static class MappedType {
        public static final String STRING = "String";
        public static final String BOOLEAN_W = "Boolean";
        public static final String INTEGER_W = "Integer";
        public static final String LONG_W = "Long";
        public static final String FLOAT_W = "Float";
        public static final String DOUBLE_W = "Double";
        public static final String DATE = "java.sql.Date";
        public static final String TIMESTAMP = "java.sql.Timestamp";
        public static final String TIME = "java.sql.Time";
        public static final String BIG_DECIMAL = "java.math.BigDecimal";
        public static final String BIG_INTEGER = "java.math.BigInteger";
        
        public static final String BYTE_A = "byte[]";
    }
}
