package org.sharpsw.kraken.data;

import java.sql.DatabaseMetaData;

public enum FKDeleteRule {

    NO_ACTION(DatabaseMetaData.importedKeyNoAction, "No action"),
    CASCADE(DatabaseMetaData.importedKeyCascade, "Cascade"),
    SET_NULL(DatabaseMetaData.importedKeySetNull, "Set null"),
    SET_DEFAULT(DatabaseMetaData.importedKeySetDefault, "Set default"),
    RESTRICT(DatabaseMetaData.importedKeyRestrict, "Restrict");

    private int code;
    private String name;

    private FKDeleteRule(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static FKDeleteRule findById(int code) {
        for(FKDeleteRule rule : values()) {
            if(rule.getCode() == code) {
                return rule;
            }
        }
        return NO_ACTION;
    }
}
