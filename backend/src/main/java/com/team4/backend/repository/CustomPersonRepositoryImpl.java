package com.team4.backend.repository;

import com.team4.backend.model.ExamplePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class CustomPersonRepositoryImpl implements CustomPersonRepository {

    private final ReactiveMongoOperations mongoOperations;

    @Autowired
    public CustomPersonRepositoryImpl(ReactiveMongoOperations mongoTemplate) {
        this.mongoOperations = mongoTemplate;
    }

    @Override
    public Flux<ExamplePerson> findAllByNameFirstLetter(String letter) {
        var regex =  letter + ".* .";
        Query query = Query.query(Criteria.where("name").regex(regex));

        return mongoOperations.find(query, ExamplePerson.class);
    }
}
