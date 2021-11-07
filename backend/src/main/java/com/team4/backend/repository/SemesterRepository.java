package com.team4.backend.repository;

import com.team4.backend.model.Semester;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends ReactiveMongoRepository<Semester, String> {
}
