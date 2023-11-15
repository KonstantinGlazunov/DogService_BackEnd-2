package de.ait.todo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * 7/13/2023
 * backend-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Profile("prod")
@Configuration
public class DigitalOceanDataSourceConfiguration {

//    @Value("${DATABASE_USERNAME}")
//    private String username;
//
//    @Value("${DATABASE_PASSWORD}")
//    private String password;
//
//    @Value("${DATABASE_HOST}")
//    private String hostname;
//
//    @Value("${DATABASE_PORT}")
//    private String port;
//
//    @Value("${DATABASE_NAME}")
//    private String database;
//
//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url("jdbc:postgresql://" + hostname +":" + port + "/" + database)
//                .username(username)
//                .password(password)
//                .build();
//    }

}

DATABASE_HOST                   jdbc:postgresql://localhost:5432/test_db
DATABASE_NAME                    test_db
DATABASE_PASSWORD                qwerty007
DATABASE_PORT					5432
DATABASE_USERNAME				postgres