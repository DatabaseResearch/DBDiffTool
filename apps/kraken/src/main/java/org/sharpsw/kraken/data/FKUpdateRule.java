package org.sharpsw.kraken.data;

import java.sql.DatabaseMetaData;

public enum FKUpdateRule {

    NO_ACTION(DatabaseMetaData.importedKeyNoAction, "No action"),
    CASCADE(DatabaseMetaData.importedKeyCascade, "Cascade"),
    SET_NULL(DatabaseMetaData.importedKeySetNull, "Set null"),
    SET_DEFAULT(DatabaseMetaData.importedKeySetDefault, "Set default"),
    RESTRICT(DatabaseMetaData.importedKeyRestrict, "Restrict");

    private int code;
    private String name;

    private FKUpdateRule(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public FKUpdateRule findById(int code) {
        for(FKUpdateRule rule : values()) {
            if(rule.getCode() == code) {
                return rule;
            }
        }
        return NO_ACTION;
    }
}
