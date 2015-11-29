package org.sharpsw.kraken.data;

import java.util.LinkedList;
import java.util.List;

public class Table {
    private String name = "";
    private String remarks = "";
    private List<Column> columns = new LinkedList<>();
    private PrimaryKey primaryKey = new PrimaryKey();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void add(Column column) {
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }
}
