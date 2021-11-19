package com.team4.backend.repository;

import com.team4.backend.model.Semester;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface SemesterRepository extends ReactiveMongoRepository<Semester, String> {

    Mono<Semester> findByFullName(String fullName);

    Mono<Semester> findByFromLessThanEqualAndToGreaterThanEqual(LocalDateTime date1, LocalDateTime date2);

}
