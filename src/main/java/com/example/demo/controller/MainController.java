package com.example.demo.controller;

import com.example.demo.model.MockRequestDto;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@AllArgsConstructor
public class MainController {

    private StudentRepository studentRepository;

    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student){
        log.info("Adding student {}", student);
        studentRepository.save(student);
    }

    @PostMapping("/addStudentList")
    public void addStudentList(@RequestBody List<Student> student){
        log.info("Adding students {}", student);
        studentRepository.saveAll(student);
    }


    @GetMapping("/getStudent/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id){
        log.info("Getting student with id {}", id);
        return new ResponseEntity<>(studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("")), HttpStatus.OK) ;
    }

    @GetMapping("/fetchStudents")
    public ResponseEntity<List<Student>> fetchStudent(){
        log.info("Fetching all students");
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/updateStudent")
    public void updateStudent(@RequestBody Student student){
        log.info("Updating student {}", student);
        studentRepository.save(student);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable String id){
        log.info("Deleting student with id {}", id);
        studentRepository.deleteById(id);
    }

    @GetMapping("/getMin")
    public ResponseEntity<Integer> getMax(){
        return new ResponseEntity(studentRepository.min(), HttpStatus.OK);
    }

    @GetMapping("/getMax")
    public ResponseEntity<Integer> getMin(){
        return new ResponseEntity(studentRepository.max(), HttpStatus.OK);
    }

    @PostMapping("/generateStudents")
    public void generateStudents(@RequestBody MockRequestDto mockRequestDto) {
        log.info("Generating students");

        Faker faker = Faker.instance();

        for (int i = 0; i < mockRequestDto.getNumberOfNewStudents(); i++) {
            Student student = new Student();
            student.setName(faker.funnyName().name());
            student.setAddress("Address " + faker.address().fullAddress());
            student.setMark(faker.number().numberBetween(0, 100));
            studentRepository.save(student);
        }

    }

    @GetMapping("/json/{name}/{mark}")
    public ResponseEntity<List<Student>> findWithJSONNameStartWithAndMarkLessThan(@PathVariable String name, @PathVariable Integer mark){
        String regex = String.format("^%s", name);
        log.info("Searching for name starting with {} with mark less than {}", regex, mark);
        return new ResponseEntity<>(studentRepository.searchWithJSON(regex, mark), HttpStatus.OK);
    }

    @GetMapping("/sql/{name}/{mark}")
    public ResponseEntity<List<Student>> findWithSQL(@PathVariable String name, @PathVariable Integer mark){
        log.info("Searching for name {} with mark {}", name, mark);
        return new ResponseEntity<>(studentRepository.searchWithSQL(name, mark), HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<Student>> findByName(@PathVariable String name){
        log.info("Searching for name {}", name);
        return new ResponseEntity<>(studentRepository.findByNameStartingWith(name), HttpStatus.OK);
    }
}