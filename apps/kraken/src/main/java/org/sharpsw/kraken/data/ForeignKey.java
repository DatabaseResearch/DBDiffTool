package org.sharpsw.kraken.data;

import java.util.LinkedList;
import java.util.List;

public class ForeignKey {
    private String name = "";
    private String primaryKeyTable = "";
    private List<ForeignKeyData> keys = new LinkedList<>();

    private FKUpdateRule updateRule = FKUpdateRule.NO_ACTION;
    private FKDeleteRule deleteRule = FKDeleteRule.NO_ACTION;
    private Deferrability deferrability = Deferrability.NOT_DEFERRABLE;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrimaryKeyTable(String table) {
        primaryKeyTable = table;
    }

    public String getPrimaryKeyTable() {
        return primaryKeyTable;
    }

    public void add(ForeignKeyData data) {
        keys.add(data);
    }

    public List<ForeignKeyData> getKeys() {
        return keys;
    }

    public void setFKDeleteRule(FKDeleteRule rule) {
        deleteRule = rule;
    }

    public FKDeleteRule getFKDeleteRule() {
        return deleteRule;
    }

    public void setFKUpdateRule(FKUpdateRule rule) {
        updateRule = rule;
    }

    public FKUpdateRule getFKUpdateRule() {
        return updateRule;
    }

    public void setDeferrability(Deferrability deferrability) {
        this.deferrability = deferrability;
    }

    public Deferrability getDeferrability() {
        return deferrability;
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

        ForeignKey instance = (ForeignKey) other;
        return instance.getName().equals(getName());
    }
}
