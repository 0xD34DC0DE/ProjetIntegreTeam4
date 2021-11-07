package com.team4.backend.repository;

import com.team4.backend.model.Semester;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends MongoRepository<Semester, String> {
}
