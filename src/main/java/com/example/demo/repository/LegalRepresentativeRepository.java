package com.example.demo.repository;

import com.example.demo.model.LegalRepresentative;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LegalRepresentativeRepository extends MongoRepository<LegalRepresentative, String> {
}
