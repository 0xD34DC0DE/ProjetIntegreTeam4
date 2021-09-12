package com.team4.backend.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/*

@Configuration
@EnableReactiveMongoRepositories("com.team4.backend.repository")
public class DatabaseConfiguration extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost/projet_integre");
    }

    @Override
    protected String getDatabaseName() {
        return "projet_integre";
    }
}
 */
