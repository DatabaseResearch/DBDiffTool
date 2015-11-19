package org.sharpsw.kraken.data;

public class Database {
    private String productName;
    private String productVersion;
    private String schema;
    private Integer minorVersion;
    private Integer majorVersion;
    private Integer minorJdbcVersion;
    private Integer majorJdbcVersion;

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

    public void setMinorJdbcVersion(Integer version) {
        minorJdbcVersion = version;
    }

    public Integer getMinorJdbcVersion() {
        return minorJdbcVersion;
    }

    public void setMajorJdbcVersion(Integer version) {
        majorJdbcVersion = version;
    }

    public Integer getMajorJdbcVersion() {
        return majorJdbcVersion;
    }
}
