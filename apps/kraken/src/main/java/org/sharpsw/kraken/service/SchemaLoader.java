package org.sharpsw.kraken.service;

import org.apache.log4j.Logger;
import org.sharpsw.kraken.data.Column;
import org.sharpsw.kraken.data.Database;
import org.sharpsw.kraken.data.SQLDataType;
import org.sharpsw.kraken.data.Table;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemaLoader {
    private static final Logger logger = Logger.getLogger(SchemaLoader.class);

    public Database load(Connection connection) throws SchemaLoaderException {
        Database database = new Database();
        try {
            loadSchemaInformation(database, connection.getMetaData());
            loadTables(database, connection.getMetaData());
            loadColumns(database, connection.getMetaData());
        } catch (SQLException exception) {
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

    private void loadTables(Database database, DatabaseMetaData metadata) throws SchemaLoaderException {
        String[] types = { "TABLE" };

        try (ResultSet rs = metadata.getTables(null, null, "%", types)) {
            logger.info("Loading tables information");
            while(rs.next()) {
                Table table = new Table();
                table.setName(rs.getString("TABLE_NAME"));
                table.setRemarks(rs.getString("REMARKS"));
                database.addTable(table);
            }
        } catch (SQLException exception) {
            throw new SchemaLoaderException(String.format("Error when loading the table information: '%s'", exception.getMessage()), exception);
        }
    }

    private void loadColumns(Database database, DatabaseMetaData metadata) throws SchemaLoaderException {
        for(Table table : database.getTables()) {
            try (ResultSet rs = metadata.getColumns(null, null, table.getName(), "%")) {
                Column column = new Column();
                configureColumnName(column, rs);
                configureColumnDataType(column, rs);
                configureColumnSize(column, rs);
                configureDecimalDigits(column, rs);
                configureIsNullable(column, rs);
                configureColumnDefaultValue(column, rs);
                configureColumnPosition(column, rs);
                configureColumnAutoIncrement(column, rs);
                configureColumnTypeName(column, rs);
                configureIsGenerated(column, rs);
            } catch (SQLException exception) {
                throw new SchemaLoaderException(String.format("Error when loading the table columns: '%s'", exception.getMessage()), exception);
            }
        }
    }

    private void configureColumnName(Column column, ResultSet rs) throws SQLException {
        column.setName(rs.getString("COLUMN_NAME"));
    }

    private void configureColumnDataType(Column column, ResultSet rs) throws SQLException {
        SQLDataType type = SQLDataType.findByType(rs.getInt("DATA_TYPE"));
        column.setDataType(type);
    }

    private void configureColumnSize(Column column, ResultSet rs) throws SQLException {
        column.setSize(rs.getInt("COLUMN_SIZE"));
    }

    private void configureDecimalDigits(Column column, ResultSet rs) throws SQLException {
        BigDecimal value = rs.getBigDecimal("DECIMAL_DIGITS");
        if(value != null) {
            column.setDecimalDigits(value.intValue());
        }
    }

    private void configureIsNullable(Column column, ResultSet rs) throws SQLException {
        String nullableValue = rs.getString("IS_NULLABLE");
        int nullable = rs.getInt("NULLABLE");

        if(nullable == DatabaseMetaData.columnNullable || "yes".equalsIgnoreCase(nullableValue)) {
            column.setIsNullable(true);
        } else {
            column.setIsNullable(false);
        }
    }

    private void configureColumnDefaultValue(Column column, ResultSet rs) throws SQLException {
        String defaultValue = rs.getString("COLUMN_DEF");

        if(defaultValue == null) {
            column.setDefaultValue("null");
        } else {
            column.setDefaultValue(defaultValue);
        }
    }

    private void configureColumnPosition(Column column, ResultSet rs) throws SQLException {
        int position = rs.getInt("ORDINAL_POSITION");
        column.setOrdinalPosition(position);
    }

    private void configureColumnAutoIncrement(Column column, ResultSet rs) throws SQLException {
        String typeName = rs.getString("TYPE_NAME");
        String isAutoIncrement = rs.getString("IS_AUTOINCREMENT");

        if(isAutoIncrement != null && isAutoIncrement.equals("YES")) {
            column.setIsAutoIncrement(true);
        } else if(typeName != null && (typeName.contains("identity") || typeName.contains("COUNTER"))) {
            column.setIsAutoIncrement(true);
        } else {
            column.setIsAutoIncrement(false);
        }
    }

    private void configureColumnTypeName(Column column, ResultSet rs) throws SQLException {
        column.setTypeName(rs.getString("TYPE_NAME"));
    }

    private void configureIsGenerated(Column column, ResultSet rs) throws SQLException {
        String value = rs.getString("IS_GENERATEDCOLUMN");
        if(value != null && "yes".equalsIgnoreCase(value)) {
            column.setIsGenerated(true);
        }
    }
}
