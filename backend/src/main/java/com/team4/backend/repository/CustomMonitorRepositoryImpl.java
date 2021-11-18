package com.team4.backend.repository;

import com.team4.backend.model.Monitor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class CustomMonitorRepositoryImpl implements CustomMonitorRepository {

    private final ReactiveMongoOperations mongoOperations;

    public CustomMonitorRepositoryImpl(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Flux<Monitor> findAllByIds(List<String> ids) {
        Query query = Query.query(Criteria.where("id").in(ids));
        return mongoOperations.find(query, Monitor.class);
    }

}