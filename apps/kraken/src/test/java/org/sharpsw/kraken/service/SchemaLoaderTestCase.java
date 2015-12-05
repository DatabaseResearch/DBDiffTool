package org.sharpsw.kraken.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharpsw.kraken.configuration.DatabaseConfiguration;
import org.sharpsw.kraken.configuration.MySQLConfiguration;
import org.sharpsw.kraken.connectivity.DatabaseConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/applicationContext.xml"})
public class SchemaLoaderTestCase {

    @Resource
    private SchemaLoader schemaLoader;

    @Resource
    private DatabaseConnectionFactory databaseConnectionFactory;

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
    public void testLoadSchemaOK() {
        
    }
}
