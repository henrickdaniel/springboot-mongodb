package com.example.demo.service;

import com.example.demo.model.LegalRepresentative;
import com.example.demo.model.MockRequestDto;
import com.example.demo.model.Student;
import com.example.demo.repository.LegalRepresentativeRepository;
import com.example.demo.repository.StudentRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class StudentService {

    public static final List<String> LIST_OF_SUBJECTS = Arrays.asList("Mathematics", "Physics", "Chemistry", "Biology", "History", "Geography", "English");
    private final StudentRepository studentRepository;
    private final LegalRepresentativeRepository legalRepresentativeRepository;

    public void generateStudents(MockRequestDto mockRequestDto){
        Faker faker = Faker.instance();

        for (int i = 0; i < mockRequestDto.getNumberOfNewStudents(); i++) {
            Student student = new Student();
            student.setName(faker.funnyName().name());
            student.setAddress("Address " + faker.address().fullAddress());
            student.setMark(faker.number().numberBetween(0, 100));
            student.setSubject(getSubjects());
            student.setLegalRepresentative(getLegalRepresentative());
            studentRepository.save(student);
        }
    }

    private LegalRepresentative getLegalRepresentative(){
        Faker faker = Faker.instance();
        LegalRepresentative legalRepresentative = new LegalRepresentative();
        legalRepresentative.setName(faker.name().name());
        legalRepresentative.setEmail(faker.internet().emailAddress());
        legalRepresentative.setPhoneNumber(faker.phoneNumber().phoneNumber());
        return legalRepresentativeRepository.save(legalRepresentative);
    }

    private List<String> getSubjects(){
        Faker faker = new Faker();
        int numberOfSubjects = faker.number().numberBetween(1,4);
        HashSet<String> subjectsSet = new HashSet<>();
        for (int i = 0; i < numberOfSubjects; i++)
            addNonRepeatedSubject(subjectsSet, faker);
        return subjectsSet.stream().toList();
    }

    private static void addNonRepeatedSubject(HashSet<String> subjectsSet, Faker faker) {
        while (!subjectsSet.add(LIST_OF_SUBJECTS.get(faker.number().numberBetween(0, LIST_OF_SUBJECTS.size() -1 ))));
    }


}
