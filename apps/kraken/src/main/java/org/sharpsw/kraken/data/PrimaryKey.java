package org.sharpsw.kraken.data;

import java.util.LinkedList;
import java.util.List;

public class PrimaryKey {
    private String name = "";
    private List<Column> columns = new LinkedList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Column column) {
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
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

        PrimaryKey instance = (PrimaryKey) other;
        return getName().equals(instance.getName());
    }
}
