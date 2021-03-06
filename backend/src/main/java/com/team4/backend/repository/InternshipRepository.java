package com.team4.backend.repository;

import com.team4.backend.model.Internship;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface InternshipRepository extends ReactiveMongoRepository<Internship, String> {

    Mono<Internship> findByStudentEmail(String studentEmail);

    Mono<Boolean> existsByStudentEmail(String studentEmail);

}
