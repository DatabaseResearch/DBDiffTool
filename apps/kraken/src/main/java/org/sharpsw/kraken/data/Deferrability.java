package org.sharpsw.kraken.data;

import java.sql.DatabaseMetaData;

public enum Deferrability {
    INITIALLY_DEFERRED(DatabaseMetaData.importedKeyInitiallyDeferred, "Initially deferred"),
    INITIALLY_IMMEDIATE(DatabaseMetaData.importedKeyInitiallyImmediate, "Initially immiedate"),
    NOT_DEFERRABLE(DatabaseMetaData.importedKeyNotDeferrable, "Not deferrable");

    private int code;
    private String name;

    private Deferrability(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Deferrability findByCode(int code) {
        for(Deferrability rule : values()) {
            if(rule.getCode() == code) {
                return rule;
            }
        }
        return NOT_DEFERRABLE;
    }
}
