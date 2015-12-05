package org.sharpsw.kraken.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.sharpsw.kraken.configuration.DatabaseConfiguration;
import org.sharpsw.kraken.configuration.MySQLConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/applicationContext.xml"})
public class SchemaLoaderTestCase {

    @Resource
    private SchemaLoader schemaLoader;

    private DatabaseConfiguration configuration;

    @Before
    public void setup() {
        configuration = new MySQLConfiguration();
        configuration.setHost("");
    }
}
