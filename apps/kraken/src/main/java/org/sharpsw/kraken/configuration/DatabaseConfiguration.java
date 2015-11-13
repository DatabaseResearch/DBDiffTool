package org.sharpsw.kraken.configuration;

public abstract class DatabaseConfiguration {
    private String host;
    private Integer port;
    private String user;
    private String password;
    private String schema;

    public DatabaseConfiguration() {
        host = "";
        port = 0;
        user = "";
        password = "";
        schema = "";
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }

    public abstract String getJdbcDriverClassname();

    public abstract String getConnectionString();
}
