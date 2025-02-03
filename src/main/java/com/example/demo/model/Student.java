package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @MongoId
    private String id;
    private String name;
    private String address;
    private Integer mark;
    private List<String> subject;

    @DBRef
    private LegalRepresentative legalRepresentative;

}
