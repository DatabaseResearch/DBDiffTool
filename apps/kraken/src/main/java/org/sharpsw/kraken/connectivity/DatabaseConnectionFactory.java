package org.sharpsw.kraken.connectivity;

import org.apache.log4j.Logger;
import org.sharpsw.kraken.configuration.DatabaseConfiguration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionFactory.class);

    public Connection createConnection(DatabaseConfiguration configuration) throws DatabaseConnectionException {
        if(logger.isInfoEnabled()) {
            logger.info("Obtaining a new database connection.");
        }

        try {
            Class.forName(configuration.getConnectionString());
            return DriverManager.getConnection(configuration.getConnectionString());
        } catch (SQLException | ClassNotFoundException exception) {
            logger.error(String.format("Error when creating a new database connection: %s", exception.getMessage()), exception);

            throw new DatabaseConnectionException(String.format("Error when creating a new connection to the database: %s", exception.getMessage()), exception);
        }
    }
}
