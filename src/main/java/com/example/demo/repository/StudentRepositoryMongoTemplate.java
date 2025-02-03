package com.example.demo.repository;

import com.example.demo.model.NameDto;
import com.example.demo.model.PushSubjectDto;
import com.example.demo.model.Student;
import com.example.demo.model.StudentNameDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    public Student addSubject(PushSubjectDto pushSubjectDto){
        Update update = new Update().push("subject", pushSubjectDto.getSubject());
        return mongoTemplate.update(Student.class)
                .matching(new Query(Criteria.where("_id").is(pushSubjectDto.getId())))
                .apply(update)
                .findAndModifyValue();
    }

    public Optional<Student> deleteSubject(PushSubjectDto pushSubjectDto){
        Update update = new Update().pull("subject", pushSubjectDto.getSubject());
        return mongoTemplate.update(Student.class)
                .matching(new Query(Criteria.where("_id").is(pushSubjectDto.getId())))
                .apply(update)
                .findAndModify();
    }

    public List<StudentNameDto> getStudentsName(){
        Query query = new Query();
        query.fields()
                .exclude("_id")
                .include("name");

        return mongoTemplate.find(query, StudentNameDto.class, "student");
    }

}
