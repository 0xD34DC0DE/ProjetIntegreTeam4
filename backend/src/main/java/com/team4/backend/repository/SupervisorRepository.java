package com.team4.backend.repository;

import com.team4.backend.model.Supervisor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends ReactiveMongoRepository<Supervisor, String> {
}