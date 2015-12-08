package org.sharpsw.kraken.service;

import org.apache.log4j.Logger;
import org.sharpsw.kraken.configuration.DatabaseConfiguration;
import org.sharpsw.kraken.connectivity.DatabaseConnectionException;
import org.sharpsw.kraken.connectivity.DatabaseConnectionFactory;
import org.sharpsw.kraken.data.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SchemaLoader {
    private static final Logger logger = Logger.getLogger(SchemaLoader.class);

    @Resource
    private TableLoader tableLoader;

    @Resource
    private ColumnLoader columnLoader;

    @Resource
    private UniqueConstraintLoader uniqueConstraintLoader;

    @Resource
    private PrimaryKeyLoader primaryKeyLoader;

    @Resource
    private ForeignKeyLoader foreignKeyLoader;

    @Resource
    private DatabaseConnectionFactory databaseConnectionFactory;

    public Database load(DatabaseConfiguration configuration) throws SchemaLoaderException {
        Database database = new Database();
        database.setSchema(configuration.getSchema());
        Connection connection = null;
        try {
            connection = databaseConnectionFactory.createConnection(configuration);
            loadSchemaInformation(database, connection.getMetaData());
            tableLoader.load(database, connection.getMetaData());
            columnLoader.load(database, connection.getMetaData());
            uniqueConstraintLoader.load(database, connection.getMetaData());
            primaryKeyLoader.load(database, connection.getMetaData());
            foreignKeyLoader.load(database, connection.getMetaData());
        } catch (SQLException | DatabaseConnectionException exception) {
            throw new SchemaLoaderException(String.format("Error when retrieving database metadata: '%s'", exception.getMessage()), exception);
        } finally {
            try {
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException exception) {}
        }

        return database;
    }

    private void loadSchemaInformation(Database db, DatabaseMetaData data) throws SchemaLoaderException {
        try {
            logger.info("Loading database information");
            db.setProductName(data.getDatabaseProductName());
            db.setMajorJDBCVersion(data.getDriverMajorVersion());
            db.setMinorJDBCVersion(data.getDriverMinorVersion());
            db.setProductVersion(data.getDatabaseProductVersion());
        } catch (SQLException exception) {
            throw new SchemaLoaderException(String.format("Error when loading database information: '%s'", exception.getMessage()), exception);
        }
    }
}
