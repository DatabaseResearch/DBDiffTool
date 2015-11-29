package org.sharpsw.kraken.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database {
    private String productName = "";
    private String productVersion = "";
    private String schema = "";
    private Integer minorVersion = 0;
    private Integer majorVersion = 0;
    private Integer minorJDBCVersion = 0;
    private Integer majorJDBCVersion = 0;
    private List<Table> tables = new ArrayList<>();
    private List<Table> views = new ArrayList<>();

    public void setProductName(String name) {
        productName = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductVersion(String version) {
        productVersion = version;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }

    public void setMinorVersion(Integer version) {
        minorVersion = version;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    public void setMajorVersion(Integer version) {
        majorVersion = version;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public void setMinorJDBCVersion(Integer version) {
        minorJDBCVersion = version;
    }

    public Integer getMinorJDBCVersion() {
        return minorJDBCVersion;
    }

    public void setMajorJDBCVersion(Integer version) {
        majorJDBCVersion = version;
    }

    public Integer getMajorJDBCVersion() {
        return majorJDBCVersion;
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public List<Table> getTables() {
        return tables;
    }

    public void addViews(Table view) {
        views.add(view);
    }

    public List<Table> getViews() {
        return views;
    }
}
