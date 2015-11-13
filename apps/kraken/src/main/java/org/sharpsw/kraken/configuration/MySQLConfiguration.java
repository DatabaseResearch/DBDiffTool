package org.sharpsw.kraken.configuration;

public class MySQLConfiguration extends DatabaseConfiguration {

    @Override
    public String getConnectionString() {
        return String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s",
                             getHost(),
                             getPort(),
                             getSchema(),
                             getUser(),
                             getPassword());
    }

    @Override
    public String getJdbcDriverClassname() {
        return "com.mysql.jdbc.Driver";
    }
}
