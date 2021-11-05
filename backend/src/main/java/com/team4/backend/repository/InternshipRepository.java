package com.team4.backend.repository;

import com.team4.backend.model.Internship;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface InternshipRepository extends ReactiveMongoRepository<Internship, String> {
    public Mono<Internship> findByStudentEmail(String studentEmail);
    public Mono<Boolean> existsByStudentEmail(String studentEmail);
}
