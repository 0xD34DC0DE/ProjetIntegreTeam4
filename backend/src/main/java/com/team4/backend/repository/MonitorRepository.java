package com.team4.backend.repository;

import com.team4.backend.model.Monitor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MonitorRepository extends ReactiveMongoRepository<Monitor, String> {

    Mono<Boolean> existsByEmailAndIsEnabledTrue(String email);

    Mono<Monitor> findByEmail(String monitorEmail);
}
