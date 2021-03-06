package org.sharpsw.kraken.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.sharpsw.kraken.data.SQLDataType.BIGINT;
import static org.sharpsw.kraken.data.SQLDataType.CHAR;
import static org.sharpsw.kraken.data.SQLDataType.DECIMAL;
import static org.sharpsw.kraken.data.SQLDataType.INTEGER;
import static org.sharpsw.kraken.data.SQLDataType.SMALLINT;
import static org.sharpsw.kraken.data.SQLDataType.TIMESTAMP;
import static org.sharpsw.kraken.data.SQLDataType.VARCHAR;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharpsw.kraken.configuration.DatabaseConfiguration;
import org.sharpsw.kraken.configuration.MySQLConfiguration;
import org.sharpsw.kraken.connectivity.DatabaseConnectionException;
import org.sharpsw.kraken.data.Column;
import org.sharpsw.kraken.data.Database;
import org.sharpsw.kraken.data.PrimaryKey;
import org.sharpsw.kraken.data.Table;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/applicationContext4UnitTesting.xml"})
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

        assertThat(table.getRemarks(), isEmptyString());
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

    @Test
    public void testPrimaryKeyLoadingOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("dbdiffut1");
        Database database = schemaLoader.load(configuration);

        List<Table> tables = database.getTables();
        Table table = tables.get(0);

        PrimaryKey pk = table.getPrimaryKey();
        assertThat(pk, notNullValue());
        assertThat(pk.getName(), is("PRIMARY"));
        assertThat(pk.getColumns(), not(empty()));
        assertThat(pk.getColumns().size(), is(1));
        assertThat(pk.getColumns().get(0), is("id"));
    }

    @Test
    public void testBigIntegerColumnOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table001");

        Column id = table.getColumns().get(0);
        assertThat(id.getName(), is("id"));
        assertThat(id.getTypeName(), is("BIGINT"));
        assertThat(id.getOrdinalPosition(), equalTo(1));
        assertThat(id.getSize(), equalTo(19));
        assertThat(id.getDataType(), is(BIGINT));
        assertThat(id.getDecimalDigits(), equalTo(0));
        assertThat(id.getDefaultValue(), is("null"));
        assertThat(id.isAutoIncrement(), equalTo(false));
        assertThat(id.isGenerated(), equalTo(false));
        assertThat(id.isNullable(), equalTo(false));
        assertThat("Field id uniqueness is must be true", id.isUnique(), equalTo(true));
    }

    @Test
    public void testVarcharNullableOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table001");

        Column name = table.getColumns().get(1);
        assertThat(name.getName(), is("name"));
        assertThat(name.getTypeName(), is("VARCHAR"));
        assertThat(name.getOrdinalPosition(), equalTo(2));
        assertThat(name.getSize(), equalTo(30));
        assertThat(name.getDataType(), is(VARCHAR));
        assertThat(name.getDecimalDigits(), equalTo(0));
        assertThat(name.getDefaultValue(), is("null"));
        assertThat(name.isNullable(), equalTo(true));
        assertThat(name.isGenerated(), equalTo(false));
        assertThat(name.isAutoIncrement(), equalTo(false));
        assertThat(name.isUnique(), equalTo(false));
    }

    @Test
    public void testCharWithDefaultValueOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table001");

        Column personType = table.getColumns().get(2);

        assertThat(personType.getName(), is("personType"));
        assertThat(personType.getTypeName(), is("CHAR"));
        assertThat(personType.getOrdinalPosition(), equalTo(3));
        assertThat(personType.getSize(), equalTo(1));
        assertThat(personType.getDataType(), is(CHAR));
        assertThat(personType.getDecimalDigits(), equalTo(0));
        assertThat(personType.getDefaultValue(), is("a"));
        assertThat(personType.isNullable(), equalTo(false));
        assertThat(personType.isGenerated(), equalTo(false));
        assertThat(personType.isAutoIncrement(), equalTo(false));
        assertThat(personType.isUnique(), equalTo(false));
    }


    @Test
    public void testIntegerNotNullUniqueOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table001");

        Column taxpayerId = table.getColumns().get(3);

        assertThat(taxpayerId.getName(), is("taxpayerId"));
        assertThat(taxpayerId.getTypeName(), is("INT"));
        assertThat(taxpayerId.getOrdinalPosition(), equalTo(4));
        assertThat(taxpayerId.getSize(), equalTo(10));
        assertThat(taxpayerId.getDataType(), is(INTEGER));
        assertThat(taxpayerId.getDecimalDigits(), equalTo(0));
        assertThat(taxpayerId.getDefaultValue(), is("null"));
        assertThat(taxpayerId.isNullable(), equalTo(false));
        assertThat(taxpayerId.isGenerated(), equalTo(false));
        assertThat(taxpayerId.isAutoIncrement(), equalTo(false));
        assertThat(taxpayerId.isUnique(), equalTo(true));
    }

    @Test
    public void testSmallintNotNullPrimaryKeyAutoIncrementOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table002");

        Column id = table.getColumns().get(0);

        assertThat(id.getName(), is("id"));
        assertThat(id.getTypeName(), is("SMALLINT"));
        assertThat(id.getOrdinalPosition(), equalTo(1));
        assertThat(id.getSize(), equalTo(5));
        assertThat(id.getDataType(), is(SMALLINT));
        assertThat(id.getDecimalDigits(), equalTo(0));
        assertThat(id.getDefaultValue(), is("null"));
        assertThat(id.isNullable(), equalTo(false));
        assertThat(id.isGenerated(), equalTo(false));
        assertThat(id.isAutoIncrement(), equalTo(true));
        assertThat(id.isUnique(), equalTo(true));
    }

    @Test
    public void testNotNullVarcharOK() throws SchemaLoaderException, SQLException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table002");

        Column name = table.getColumns().get(1);

        assertThat(name.getName(), is("name"));
        assertThat(name.getTypeName(), is("VARCHAR"));
        assertThat(name.getOrdinalPosition(), equalTo(2));
        assertThat(name.getSize(), equalTo(23));
        assertThat(name.getDataType(), is(VARCHAR));
        assertThat(name.getDecimalDigits(), equalTo(0));
        assertThat(name.getDefaultValue(), is("null"));
        assertThat(name.isNullable(), equalTo(false));
        assertThat(name.isGenerated(), equalTo(false));
        assertThat(name.isAutoIncrement(), equalTo(false));
        assertThat(name.isUnique(), equalTo(false));
    }

    @Test
    public void testDecimalColumnNotNullOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table002");

        Column column = table.getColumns().get(2);

        assertThat(column.getName(), is("salary"));
        assertThat(column.getTypeName(), is("DECIMAL"));
        assertThat(column.getOrdinalPosition(), equalTo(3));
        assertThat(column.getSize(), equalTo(10));
        assertThat(column.getDataType(), is(DECIMAL));
        assertThat(column.getDecimalDigits(), equalTo(3));
        assertThat(column.getDefaultValue(), is("null"));
        assertThat(column.isNullable(), equalTo(false));
        assertThat(column.isGenerated(), equalTo(false));
        assertThat(column.isAutoIncrement(), equalTo(false));
        assertThat(column.isUnique(), equalTo(false));
    }

    @Test
    public void testDateTimeNullableOK() throws SQLException, SchemaLoaderException {
        configuration.setSchema("TestCase2");
        Database database = schemaLoader.load(configuration);
        Table table = database.findByName("Table002");

        Column column = table.getColumns().get(3);

        assertThat(column.getName(), is("hiringDate"));
        assertThat(column.getTypeName(), is("DATETIME"));
        assertThat(column.getOrdinalPosition(), equalTo(4));
        assertThat(column.getSize(), equalTo(19));
        assertThat(column.getDataType(), is(TIMESTAMP));
        assertThat(column.getDecimalDigits(), equalTo(0));
        assertThat(column.getDefaultValue(), is("null"));
        assertThat(column.isNullable(), equalTo(true));
        assertThat(column.isGenerated(), equalTo(false));
        assertThat(column.isAutoIncrement(), equalTo(false));
        assertThat(column.isUnique(), equalTo(false));
    }
}
