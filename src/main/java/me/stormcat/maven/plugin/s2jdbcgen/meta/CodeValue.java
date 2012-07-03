package me.stormcat.maven.plugin.s2jdbcgen.meta;

/**
 * @author a.yamada
 * 
 */
public class CodeValue {

    private String value;

    private String label;

    private String field;

    public CodeValue() {

    }

    /**
     * コンストラクタ
     * @param value
     * @param label
     */
    public CodeValue(String value, String label, String field) {
        this.value = value;
        this.label = label;
        this.field = field;
    }

    /**
     * value を取得します。
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * valueを設定します。
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * label を取得します。
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * labelを設定します。
     * @param label label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * field を取得します。
     * @return field
     */
    public String getField() {
        return field;
    }

    /**
     * fieldを設定します。
     * @param field field
     */
    public void setField(String field) {
        this.field = field;
    }

}
