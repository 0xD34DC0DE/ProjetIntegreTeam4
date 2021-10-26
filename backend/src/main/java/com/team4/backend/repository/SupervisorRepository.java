package com.team4.backend.repository;

import com.team4.backend.model.Supervisor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SupervisorRepository extends ReactiveMongoRepository<Supervisor, String> {
    public Mono<Supervisor> findSupervisorByEmail(String email);
}
