package org.sharpsw.kraken.service;

import org.sharpsw.kraken.data.Database;
import org.sharpsw.kraken.data.PrimaryKey;
import org.sharpsw.kraken.data.Table;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PrimaryKeyLoader {
    public void load(Database database, DatabaseMetaData metadata) throws SchemaLoaderException {
        for(Table table : database.getTables()) {
            try (ResultSet rs = metadata.getPrimaryKeys(null, null, table.getName())) {
                PrimaryKey primaryKey = new PrimaryKey();
                while(rs.next()) {
                    String name = rs.getString("PK_NAME");
                    String column = rs.getString("COLUMN_NAME");
                    primaryKey.setName(name);
                    primaryKey.add(column);
                }
                table.setPrimaryKey(primaryKey);
            } catch (SQLException exception) {
                throw new SchemaLoaderException(String.format("Error when loading the primary key information: '%s'", exception.getMessage()), exception);
            }
        }
    }
}
