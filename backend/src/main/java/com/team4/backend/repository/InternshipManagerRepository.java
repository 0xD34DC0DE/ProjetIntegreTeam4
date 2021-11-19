package com.team4.backend.repository;

import com.team4.backend.model.InternshipManager;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface InternshipManagerRepository extends ReactiveMongoRepository<InternshipManager, String> {

    Mono<InternshipManager> findByEmail(String internshipManagerEmail);

}
