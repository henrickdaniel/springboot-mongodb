package com.example.demo.repository;

import com.example.demo.model.NameDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class StudentRepositoryMongoTemplate {

    private final MongoTemplate mongoTemplate;

    public List<NameDto> find(String name){
        Query query = new BasicQuery(String.format("""
            {
                name: {$regex: "^%s"}
            }
        """, name));
        return this.mongoTemplate.find(query, NameDto.class, "student");
    }

}
