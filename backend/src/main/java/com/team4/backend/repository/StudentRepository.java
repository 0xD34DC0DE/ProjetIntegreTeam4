package com.team4.backend.repository;

import com.team4.backend.model.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String>, CustomStudentRepository {
    Mono<Student> findByEmailAndIsEnabledTrue(String studentEmail);

    Mono<Student> findByEmail(String email);

}
