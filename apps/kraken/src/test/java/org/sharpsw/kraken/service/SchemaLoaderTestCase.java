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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.SQLException;

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
        configuration.setSchema("dbdiff");
        configuration.setPort(3306);
        configuration.setUser("dbdiff");
        configuration.setPassword("dbdiff");
    }

    @Test
    public void testLoadSchemaOK() throws SQLException, DatabaseConnectionException, SchemaLoaderException {
        Database database = schemaLoader.load(configuration);
        Assert.assertThat(database.getProductName(), Matchers.is("MySQL"));
        Assert.assertThat(database.getSchema(), Matchers.is("dbdiff"));
    }
}
