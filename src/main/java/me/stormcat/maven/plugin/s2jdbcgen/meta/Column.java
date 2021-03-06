/**
 *
 */
package me.stormcat.maven.plugin.s2jdbcgen.meta;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.stormcat.maven.plugin.s2jdbcgen.Constants.ColumnMetaColumn;
import me.stormcat.maven.plugin.s2jdbcgen.Constants.MappedType;
import me.stormcat.maven.plugin.s2jdbcgen.ModelMeta;
import me.stormcat.maven.plugin.s2jdbcgen.util.StringUtil;
import net.arnx.jsonic.JSON;

/**
 * @author a.yamada
 * 
 */
public class Column {

    private final String tableCat;

    private final String tableSchem;

    private final String tableName;

    private final String columnName;

    private final int dataType;

    private final String typeName;

    private final int columnSize;

    private final int bufferLength;

    private final int decimalDigits;

    private final int numPrecRadix;

    private final boolean nullable;

    private final String remarks;

    private final String columnDef;

    private final int sqlDataType;

    private final String sqlDatetimeSub;

    private final int charOctetLength;

    private final int ordinalPosition;

    private final String scopeCatalog;

    private final String scopeSchema;

    private final String scopeTable;

    private final int sourceDataType;

    private final boolean autoincrement;

    private final boolean primaryKey;

    private final String fieldName;

    private ModelMeta referencedModel;

    public Column(ResultSet resultSet, Set<String> primaryKeySet) {
        try {
            tableCat = resultSet.getString(ColumnMetaColumn.TABLE_CAT);
            tableSchem = resultSet.getString(ColumnMetaColumn.TABLE_SCHEM);
            tableName = resultSet.getString(ColumnMetaColumn.TABLE_NAME);
            columnName = resultSet.getString(ColumnMetaColumn.COLUMN_NAME);
            dataType = resultSet.getInt(ColumnMetaColumn.DATA_TYPE);
            typeName = resultSet.getString(ColumnMetaColumn.TYPE_NAME);
            columnSize = resultSet.getInt(ColumnMetaColumn.COLUMN_SIZE);
            bufferLength = resultSet.getInt(ColumnMetaColumn.BUFFER_LENGTH);
            decimalDigits = resultSet.getInt(ColumnMetaColumn.DECIMAL_DIGITS);
            numPrecRadix = resultSet.getInt(ColumnMetaColumn.NUM_PREC_RADIX);
            nullable = resultSet.getBoolean(ColumnMetaColumn.NULLABLE);
            remarks = resultSet.getString(ColumnMetaColumn.REMARKS);
            columnDef = resultSet.getString(ColumnMetaColumn.COLUMN_DEF);
            sqlDataType = resultSet.getInt(ColumnMetaColumn.SQL_DATA_TYPE);
            sqlDatetimeSub = resultSet.getString(ColumnMetaColumn.SQL_DATETIME_SUB);
            charOctetLength = resultSet.getInt(ColumnMetaColumn.CHAR_OCTET_LENGTH);
            ordinalPosition = resultSet.getInt(ColumnMetaColumn.ORDINAL_POSITION);
            scopeCatalog = resultSet.getString(ColumnMetaColumn.SCOPE_CATALOG);
            scopeSchema = resultSet.getString(ColumnMetaColumn.SCOPE_SCHEMA);
            scopeTable = resultSet.getString(ColumnMetaColumn.SCOPE_TABLE);
            sourceDataType = resultSet.getInt(ColumnMetaColumn.SOURCE_DATA_TYPE);
            autoincrement = resultSet.getBoolean(ColumnMetaColumn.IS_AUTOINCREMENT);

            primaryKey = primaryKeySet.contains(columnName);
            fieldName = StringUtil.camelizeMethod(columnName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getTableCat() {
        return tableCat;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getDataType() {
        return dataType;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int getBufferLength() {
        return bufferLength;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public int getNumPrecRadix() {
        return numPrecRadix;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public int getSqlDataType() {
        return sqlDataType;
    }

    public String getSqlDatetimeSub() {
        return sqlDatetimeSub;
    }

    public int getCharOctetLength() {
        return charOctetLength;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public String getScopeCatalog() {
        return scopeCatalog;
    }

    public String getScopeSchema() {
        return scopeSchema;
    }

    public String getScopeTable() {
        return scopeTable;
    }

    public int getSourceDataType() {
        return sourceDataType;
    }

    public boolean isAutoincrement() {
        return autoincrement;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isUnsigned() {
        return typeName.contains("UNSIGNED");
    }

    public String getJavaType() {
        // TODO mysql限定実装
        switch (dataType) {
            case Types.BIT:
                return columnSize > 1 ? MappedType.BYTE_A : MappedType.BOOLEAN_W;
            case Types.TINYINT:
                // TODO tinyInt1isBit=trueで、サイズ1の場合Boolean
                // それ以外ならInteger
                return MappedType.INTEGER_W;
            case Types.BOOLEAN:
                return MappedType.BOOLEAN_W;
            case Types.SMALLINT:
                return MappedType.INTEGER_W;
            case Types.INTEGER:
                return isUnsigned() ? MappedType.LONG_W : MappedType.INTEGER_W;
            case Types.BIGINT:
                return isUnsigned() ? MappedType.BIG_INTEGER : MappedType.LONG_W;
            case Types.FLOAT:
                return MappedType.FLOAT_W;
            case Types.DOUBLE:
                return MappedType.DOUBLE_W;
            case Types.DECIMAL:
                return MappedType.BIG_DECIMAL;
            case Types.DATE:
                return MappedType.DATE;
            case Types.TIMESTAMP:
                return MappedType.TIMESTAMP;
            case Types.TIME:
                return MappedType.TIME;
            case Types.CHAR:
                // TODO カラムの文字セットが BINARY の場合は byte[] が戻される
                return MappedType.STRING;
            case Types.VARCHAR:
                // TODO カラムの文字セットが BINARY の場合は byte[] が戻される
                return MappedType.STRING;
            case Types.BINARY:
                return MappedType.BYTE_A;
            case Types.VARBINARY:
                return MappedType.BYTE_A;
            case Types.BLOB:
                return MappedType.BYTE_A;
            case Types.LONGVARCHAR:
                return MappedType.STRING;
            default:
                return MappedType.STRING;
        }
    }

    public boolean isStringType() {
        // TODO mysql限定実装
        return (dataType == Types.CHAR || dataType == Types.VARCHAR || dataType == Types.LONGVARCHAR);
    }

    public boolean isNumberType() {
        return (dataType == Types.TINYINT || dataType == Types.SMALLINT || dataType == Types.INTEGER || dataType == Types.BIGINT);
    }

    public String getColumnAnnotation() {
        StringBuilder builder = new StringBuilder("@javax.persistence.Column(");
        // nullable
        builder.append("nullable = ");
        builder.append(nullable);
        // unique
        // insertable
        // updatable
        // length
        if (isStringType()) {
            builder.append(", ");
            builder.append("length = ");
            builder.append(columnSize);
        } else if (isNumberType()) {
            builder.append(", ");
            builder.append("precision = ");
            builder.append(columnSize);
        }
        // precision
        // scale

        builder.append(")");
        return builder.toString();
    }

    public String getTemporal() {
        if (dataType == Types.DATE) {
            return "DATE";
        } else if (dataType == Types.TIME) {
            return "TIME";
        } else if (dataType == Types.TIMESTAMP) {
            return "TIMESTAMP";
        }
        return null;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public String getReferenceTableName() {
        // TODO 外部キー実装
        if (StringUtil.isBlank(remarks)) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\[=>(.+)\\]");
        Matcher matcher = pattern.matcher(remarks);
        return matcher.find() ? matcher.group(1) : null;
    }

    public CodeDef getCodeDef() {
        if (StringUtil.isBlank(remarks)) {
            return null;
        }

        try {
            CodeValue[] values = JSON.decode(remarks, CodeValue[].class);
            String enumName = org.seasar.util.lang.StringUtil.capitalize(getFieldName());
            CodeDef codeDef = new CodeDef(enumName, getJavaType());
            codeDef.addCodeValues(Arrays.asList(values));

            return codeDef;
        } catch (Exception e) {
            return null;
        }
    }

    public ModelMeta getReferencedModel() {
        return referencedModel;
    }

    public void setReferencedModel(ModelMeta referencedModel) {
        this.referencedModel = referencedModel;
    }

    public String getReferenceFieldName() {
        if (referencedModel == null) {
            return null;
        }
        return String.format("%sBy%s%s", referencedModel.getFieldName(), fieldName.substring(0, 1).toUpperCase(),
                fieldName.substring(1, fieldName.length()));
    }

}
