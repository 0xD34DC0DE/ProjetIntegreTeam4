package com.team4.backend.repository;

import com.team4.backend.model.Internship;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface InternshipRepository extends ReactiveMongoRepository<Internship, String> {
}
