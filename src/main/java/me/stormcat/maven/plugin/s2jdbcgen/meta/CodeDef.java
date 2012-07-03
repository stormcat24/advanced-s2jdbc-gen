package me.stormcat.maven.plugin.s2jdbcgen.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author a.yamada
 * 
 */
public class CodeDef {

    private final String enumName;

    private final String keyType;

    private final List<CodeValue> codeValues;

    /**
     * コンストラクタ
     * @param enumName
     * @param keyType
     */
    public CodeDef(String enumName, String keyType) {
        this.enumName = enumName;
        this.keyType = keyType;
        codeValues = new ArrayList<CodeValue>();
    }

    /**
     * enumName を取得します。
     * @return enumName
     */
    public String getEnumName() {
        return enumName;
    }

    /**
     * keyType を取得します。
     * @return keyType
     */
    public String getKeyType() {
        return keyType;
    }

    /**
     * codeValues を取得します。
     * @return codeValues
     */
    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public void addCodeValue(CodeValue codeValue) {
        codeValues.add(codeValue);
    }

    public void addCodeValues(Collection<CodeValue> codeValues) {
        this.codeValues.addAll(codeValues);
    }

}
