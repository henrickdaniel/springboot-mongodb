package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.LegalRepresentativeRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentRepositoryMongoTemplate;
import com.example.demo.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@AllArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentRepositoryMongoTemplate mongoTemplate;
    private final LegalRepresentativeRepository legalRepresentativeRepository;
    private final StudentService studentService;

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
        studentService.generateStudents(mockRequestDto);
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

    @GetMapping("/{name}")
    public ResponseEntity<List<NameDto>> findAll(@PathVariable String name){
        return new ResponseEntity<>(mongoTemplate.find(name), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<SubjectCount>> countSubject(){
        log.info("Counting subjects");
        List<SubjectCount> list = studentRepository.countSubject();
        log.info("List with {} students", list.size());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/countSubject/{subject}")
    public ResponseEntity<Long> countSubject(@PathVariable String subject){
        return new ResponseEntity<>(studentRepository.countSubject(subject), HttpStatus.OK);
    }

    @GetMapping("/countSubjectCountQuery/{subject}")
    public ResponseEntity<Long> countSubjectCountQuery(@PathVariable String subject){
        return new ResponseEntity<>(studentRepository.countSubjectCountQuery(subject), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        log.info("Deleting student with id {})", id);
        studentRepository.deleteQuery(id);
        return new ResponseEntity<String>(String.format("Deleted student with id %s", id), HttpStatus.OK);
    }

    @PutMapping("/addSubject")
    public ResponseEntity<Student> addSubject(@RequestBody PushSubjectDto pushSubjectDto){
        log.info("Adding subject {} to student with id {}", pushSubjectDto.getSubject(), pushSubjectDto.getId());
        return new ResponseEntity<>(mongoTemplate.addSubject(pushSubjectDto), HttpStatus.OK);
    }

    @PutMapping("/deleteSubject")
    public ResponseEntity<Student> deleteSubject(@RequestBody PushSubjectDto pushSubjectDto){
        log.info("Deleting subject {} to student with id {}", pushSubjectDto.getSubject(), pushSubjectDto.getId());
        return new ResponseEntity<>(mongoTemplate.deleteSubject(pushSubjectDto).orElseThrow(() -> new NoSuchElementException("Student not found.")), HttpStatus.OK);
    }

    @GetMapping("/studentsName")
    public ResponseEntity<List<StudentNameDto>> getStudentsName(){
        return new ResponseEntity<>(mongoTemplate.getStudentsName(), HttpStatus.OK);
    }

}