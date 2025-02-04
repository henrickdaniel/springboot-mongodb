package com.example.demo.service;

import com.example.demo.repository.MongoClientInstance;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectionRepository {

    public List<String> getDatabases(){
        MongoClient ongoClient = MongoClientInstance.getMongoClient();
        List<Document> documents = ongoClient.listDatabases().into(new ArrayList<>());
        return RepositoryUtil.toStringList(documents, "name");
    }

}
