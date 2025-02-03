package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class LegalRepresentative {

    @MongoId
    private String _id;
    private String name;
    private String email;
    private String phoneNumber;

}
