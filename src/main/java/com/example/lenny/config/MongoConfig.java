package com.example.lenny.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.lenny.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.host}")
    private String mongoDBHost;

    @Override
    protected String getDatabaseName() {
        return "lenny";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://"+mongoDBHost+":27017");
    }
}
