package com.example.demo.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class Movie {

    @MongoId
    private String _id;
    private String title;
    private String plot;
    private List<String> genres;
    private Integer runtime;
    private List<String> cast;
    private String fullplot;
    private List<String> languages;
    private LocalDateTime released;
    private List<String> directors;
    private String rated;


}
