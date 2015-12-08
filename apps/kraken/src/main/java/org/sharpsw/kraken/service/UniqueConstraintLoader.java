package org.sharpsw.kraken.service;

import org.sharpsw.kraken.data.Column;
import org.sharpsw.kraken.data.Database;
import org.sharpsw.kraken.data.Table;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@Component
public class UniqueConstraintLoader {

    public void load(Database database, DatabaseMetaData metadata) throws SchemaLoaderException {
        for(Table table : database.getTables()) {
            try (ResultSet rs = metadata.getIndexInfo(null, null, table.getName(), true, true)) {
                while(rs.next()) {
                    String indexName = rs.getString("INDEX_NAME");
                    if(indexName == null) {
                        continue;
                    }

                    String columnName = rs.getString("COLUMN_NAME");
                    Column column = table.findByName(columnName);
                    if(column != null) {
                        column.setIsUnique(true);
                    }
                }
            } catch (SQLException exception) {
                throw new SchemaLoaderException(String.format("Error when loading the table columns: '%s'", exception.getMessage()), exception);
            }
        }
    }
}
