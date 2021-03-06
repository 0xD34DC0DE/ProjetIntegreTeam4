package com.team4.backend.repository;

import com.team4.backend.model.Evaluation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EvaluationRepository extends ReactiveMongoRepository<Evaluation, String> {

}
