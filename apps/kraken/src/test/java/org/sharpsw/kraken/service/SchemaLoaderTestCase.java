package org.sharpsw.kraken.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharpsw.kraken.configuration.DatabaseConfiguration;
import org.sharpsw.kraken.configuration.MySQLConfiguration;
import org.sharpsw.kraken.connectivity.DatabaseConnectionException;
import org.sharpsw.kraken.data.Column;
import org.sharpsw.kraken.data.Database;
import org.sharpsw.kraken.data.Table;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.sharpsw.kraken.data.SQLDataType.INTEGER;
import static org.sharpsw.kraken.data.SQLDataType.VARCHAR;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/applicationContext.xml"})
public class SchemaLoaderTestCase {

    @Resource
    private SchemaLoader schemaLoader;

    private DatabaseConfiguration configuration;

    @Before
    public void setup() {
        configuration = new MySQLConfiguration();
        configuration.setHost("localhost");
        configuration.setPort(3306);

        configuration.setSchema("dbdiff");
        configuration.setUser("dbdiff");
        configuration.setPassword("dbdiff");
    }

    @Test
    public void testLoadEmptySchemaOK() throws SQLException, DatabaseConnectionException, SchemaLoaderException {
        Database database = schemaLoader.load(configuration);
        assertThat(database.getProductName(), is("MySQL"));
        assertThat(database.getSchema(), is("dbdiff"));
        assertThat(database.getTables(), empty());
        assertThat(database.getViews(), empty());
    }

    @Test
    public void testLoadSchemaOK() throws SQLException, DatabaseConnectionException, SchemaLoaderException {
        configuration.setSchema("dbdiffut1");
        Database database = schemaLoader.load(configuration);
        assertThat(database.getProductName(), is("MySQL"));
        assertThat(database.getSchema(), is("dbdiffut1"));
        assertThat(database.getTables(), not(empty()));
        assertThat(database.getViews(), empty());

        List<Table> tables = database.getTables();
        Table table = tables.get(0);
        assertThat(table.getName(), is("table001"));
        assertThat(table.getColumns(), not(empty()));
        assertThat(table.getColumns().size(), equalTo(2));
        assertThat(table.getRemarks(), isEmptyString());
        assertThat(table.getForeignKeys(), notNullValue());



    }

    @Test
    public void testLoadTablesOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("dbdiffut1");
        Database database = schemaLoader.load(configuration);
        assertThat(database.getTables(), not(empty()));
        assertThat(database.getTables().size(), equalTo(1));

        List<Table> tables = database.getTables();
        Table table = tables.get(0);
        assertThat(table.getName(), is("table001"));

        assertThat(table.getColumns(), not(empty()));
        assertThat(table.getColumns().size(), equalTo(2));

        assertThat(table.getRemarks(), isEmptyString());

        assertThat(table.getForeignKeys(), notNullValue());
        assertThat(table.getForeignKeys().size(), equalTo(0));

        assertThat(table.getPrimaryKey(), notNullValue());
    }

    @Test
    public void testLoadColumnsOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("dbdiffut1");
        Database database = schemaLoader.load(configuration);

        List<Table> tables = database.getTables();
        Table table = tables.get(0);

        assertThat(table.getColumns(), not(empty()));
        assertThat(table.getColumns().size(), equalTo(2));

        Column idColumn = table.getColumns().get(0);
        assertThat(idColumn.getName(), is("id"));
        assertThat(idColumn.getDataType(), is(INTEGER));
        assertThat(idColumn.getOrdinalPosition(), equalTo(1));
        assertThat(idColumn.getDefaultValue(), is("null"));
        assertThat(idColumn.getDecimalDigits(), equalTo(0));
        assertThat(idColumn.getSize(), equalTo(10));
        assertThat(idColumn.getTypeName(), is("INT"));

        Column nameColumn = table.getColumns().get(1);
        assertThat(nameColumn.getName(), is("name"));
        assertThat(nameColumn.getDataType(), is(VARCHAR));
        assertThat(nameColumn.getOrdinalPosition(), equalTo(2));
        assertThat(nameColumn.getDefaultValue(), is("null"));
        assertThat(nameColumn.getDecimalDigits(), equalTo(0));
        assertThat(nameColumn.getSize(), equalTo(50));
        assertThat(nameColumn.getTypeName(), is("VARCHAR"));
    }
}
