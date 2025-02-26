package com.allMighty.config.general;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Bean
    public DefaultConfiguration jooqConfiguration(DataSource dataSource) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setSQLDialect(SQLDialect.POSTGRES); // Set the dialect explicitly
        return configuration;
    }

    @Bean
    public DSLContext dsl(DefaultConfiguration configuration) {
        return configuration.dsl();
    }
}
