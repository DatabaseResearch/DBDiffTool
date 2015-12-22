package org.sharpsw.kraken.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.sharpsw.kraken.data.SQLDataType.BIGINT;

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
import org.sharpsw.kraken.data.Table;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void testLoadDBDiffUT1SchemaOK() throws SQLException, DatabaseConnectionException, SchemaLoaderException {
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

        Column col1 = table.getColumns().get(0);
        assertThat(col1.getName(), is("id"));
        assertThat(col1.getDataType(), is(BIGINT));
    }
}
