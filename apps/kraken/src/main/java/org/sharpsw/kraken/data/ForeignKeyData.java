package org.sharpsw.kraken.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class ForeignKeyData {
    private Map<String, String> columns = new LinkedHashMap<>();

    public void addForeignKeyColumns(String sourceColumn, String targetColumn) {
        columns.put(sourceColumn, targetColumn);
    }

    public Map<String, String> getForeignKeyColumns() {
        return columns;
    }
}
