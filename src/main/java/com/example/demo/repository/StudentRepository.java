package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    @Aggregation(pipeline = "{$group: {_id: '', maximo: {$max: '$mark'}}}")
    Integer max();

    @Aggregation(pipeline = "{$group: {_id: '', minimo: {$min: '$mark'}}}")
    Integer min();

    List<Student> findByNameStartingWith(String name);

    @Query(value = """ 
    {
        $or: [
            {name: {$regex: ?0}},
            {mark: {$lt: ?1}}
        ]
    }
    """, fields = "name")
    List<Student> searchWithJSON(String name, Integer mark);

    @Query("select * from student where nome like ?1 and mark < ?2")
    List<Student> searchWithSQL(String name, Integer mark);

}
