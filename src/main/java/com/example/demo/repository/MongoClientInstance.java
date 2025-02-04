package com.example.demo.repository;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MongoClientInstance {

    private static MongoClient mongoClient;

    @Value("CONNECTION_URL")
    String connectionUrl;

    @Autowired
    private Environment environment;


    public MongoClient getMongoClient(){

        if(mongoClient == null){
            ConnectionString connectionString = new ConnectionString(environment.getProperty("CONNECTION_URL"));
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                    .build();
            mongoClient = MongoClients.create(settings);
        }
        return mongoClient;
    }
}
