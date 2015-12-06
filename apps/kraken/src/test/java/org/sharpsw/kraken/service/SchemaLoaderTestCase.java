package org.sharpsw.kraken.service;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharpsw.kraken.configuration.DatabaseConfiguration;
import org.sharpsw.kraken.configuration.MySQLConfiguration;
import org.sharpsw.kraken.connectivity.DatabaseConnectionException;
import org.sharpsw.kraken.data.Database;
import org.sharpsw.kraken.data.Table;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

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
        Assert.assertThat(database.getProductName(), Matchers.is("MySQL"));
        Assert.assertThat(database.getSchema(), Matchers.is("dbdiff"));
        Assert.assertThat(database.getTables(), Matchers.empty());
        Assert.assertThat(database.getViews(), Matchers.empty());
    }

    @Test
    public void testLoadDBDiffUT1SchemaOK() throws SQLException, DatabaseConnectionException, SchemaLoaderException {
        configuration.setSchema("dbdiffut1");
        Database database = schemaLoader.load(configuration);
        Assert.assertThat(database.getProductName(), Matchers.is("MySQL"));
        Assert.assertThat(database.getSchema(), Matchers.is("dbdiffut1"));
        Assert.assertThat(database.getTables(), Matchers.not(Matchers.empty()));
        Assert.assertThat(database.getViews(), Matchers.empty());

        List<Table> tables = database.getTables();
        Table table = tables.get(0);
        Assert.assertThat(table.getName(), Matchers.is("table001"));
        Assert.assertThat(table.getColumns(), Matchers.not(Matchers.empty()));
    }
}
