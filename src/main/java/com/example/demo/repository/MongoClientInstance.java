package com.example.demo.repository;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoClientInstance {

    private static MongoClient mongoClient;

    public static synchronized MongoClient getMongoClient(){
        if(mongoClient == null){
            ConnectionString connectionString = new ConnectionString("mongodb+srv://henrickdaniel:8jnp0hyM8m2WJstz@cluster0.f5fdk.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                    .build();
            mongoClient = MongoClients.create(settings);
        }
        return mongoClient;
    }
}
