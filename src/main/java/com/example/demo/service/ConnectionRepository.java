package com.example.demo.service;

import com.example.demo.repository.MongoClientInstance;
import com.mongodb.client.MongoClient;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class ConnectionRepository {

    private final MongoClientInstance mongoClientInstance;

    public List<String> getDatabases(){
        MongoClient ongoClient = mongoClientInstance.getMongoClient();
        List<Document> documents = ongoClient.listDatabases().into(new ArrayList<>());
        return RepositoryUtil.toStringList(documents, "name");
    }

}
