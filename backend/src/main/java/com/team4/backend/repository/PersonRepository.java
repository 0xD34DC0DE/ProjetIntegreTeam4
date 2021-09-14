package com.team4.backend.repository;

import com.team4.backend.model.ExamplePerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<ExamplePerson, String>, CustomPersonRepository {
    Mono<ExamplePerson> findByName(String name);
}
