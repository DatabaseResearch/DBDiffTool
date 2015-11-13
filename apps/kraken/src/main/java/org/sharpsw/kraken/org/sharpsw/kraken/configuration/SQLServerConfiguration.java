package org.sharpsw.kraken.org.sharpsw.kraken.configuration;

public class SQLServerConfiguration extends DatabaseConfiguration {
    private String namedInstance;

    public void setNamedInstance(String instance) {
        namedInstance = instance;
    }

    public String getNamedInstance() {
        return namedInstance;
    }

    @Override
    public String getJdbcDriverClassname() {
        return "net.sourceforge.jtds.jdbc.Driver";
    }

    @Override
    public String getConnectionString() {
        if(namedInstance == null || namedInstance.isEmpty()) {
            return String.format("jdbc:jtds:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s",
                                 getHost(),
                                 getPort(),
                                 getSchema(),
                                 getUser(),
                                 getPassword());
        }
        return String.format("jdbc:jtds:sqlserver://%s\\%s:%d;databaseName=%s;user=%s;password=%s",
                             getHost(),
                             getNamedInstance(),
                             getPort(),
                             getSchema(),
                             getUser(),
                             getPassword());
    }
}
