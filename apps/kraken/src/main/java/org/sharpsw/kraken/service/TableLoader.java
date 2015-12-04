package org.sharpsw.kraken.service;

import org.apache.log4j.Logger;
import org.sharpsw.kraken.data.Database;
import org.sharpsw.kraken.data.Table;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TableLoader {
    private static final Logger logger = Logger.getLogger(TableLoader.class);

    public void load(Database database, DatabaseMetaData metadata) throws SchemaLoaderException {
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
}
