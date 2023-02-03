package com.eberry.transactionservice.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
@Profile("!test")
public class PostgresConfig extends AbstractR2dbcConfiguration {

    @Value("${db.host}")
    private String dbHost;

    @Value("${db.port}")
    private Integer dbPort;

    @Value("${db.name}")
    private String dbName;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder().host(dbHost).port(dbPort)
                .username(username).password(password).database(dbName).build());
    }

}
