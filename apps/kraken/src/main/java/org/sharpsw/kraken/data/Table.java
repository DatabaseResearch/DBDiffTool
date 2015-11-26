package org.sharpsw.kraken.data;

/**
 * Created by andersonkmi on 11/26/15.
 */
public class Table {
    private String name = "";
    private String remarks = "";

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
}
