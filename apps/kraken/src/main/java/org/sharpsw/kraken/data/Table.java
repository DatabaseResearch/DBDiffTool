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

    public void setPrimaryKey(PrimaryKey key) {
        primaryKey = key;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(!getClass().equals(other.getClass())) {
            return false;
        }

        Table instance = (Table) other;

        return getName().equals(instance.getName());
    }
}
