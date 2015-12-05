package org.sharpsw.kraken.service;

import org.sharpsw.kraken.data.*;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ForeignKeyLoader {

    public void load(Database database, DatabaseMetaData metadata) throws SchemaLoaderException {
        for(Table table : database.getTables()) {
            try (ResultSet rs = metadata.getImportedKeys(null, null, table.getName())) {
                String name = rs.getString("FK_NAME");
                ForeignKey fk = null;
                if(table.getForeignKeys().containsKey(name)) {
                    fk = table.getForeignKeys().get(name);
                } else {
                    fk = new ForeignKey();
                    fk.setName(name);
                    fk.setPrimaryKeyTable(rs.getString("PKTABLE_NAME"));

                    fk.setFKDeleteRule(FKDeleteRule.findById(rs.getInt(rs.getInt("DELETE_RULE"))));
                    fk.setFKUpdateRule(FKUpdateRule.findById(rs.getInt(rs.getInt("DELETE_RULE"))));
                    fk.setDeferrability(Deferrability.findByCode(rs.getInt("DEFERRABILITY")));
                }

                ForeignKeyData foreignKeyData = new ForeignKeyData();
                String targetColumn = rs.getString("PKCOLUMN_NAME");
                String sourceColumn = rs.getString("FKCOLUMN_NAME");

                foreignKeyData.addForeignKeyColumns(sourceColumn, targetColumn);
                fk.add(foreignKeyData);
            } catch (SQLException exception) {
                throw new SchemaLoaderException(String.format("Error when loading foreign key information: '%s'", exception.getMessage()), exception);
            }
        }
    }
}
