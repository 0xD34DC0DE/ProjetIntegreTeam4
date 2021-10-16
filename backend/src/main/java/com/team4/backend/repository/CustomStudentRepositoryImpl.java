package com.team4.backend.repository;

import com.team4.backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class CustomStudentRepositoryImpl implements CustomStudentRepository {

    private final ReactiveMongoOperations mongoOperations;

    @Autowired
    public CustomStudentRepositoryImpl(ReactiveMongoOperations mongoTemplate) {
        this.mongoOperations = mongoTemplate;
    }

    @Override
    public Flux<Student> findAllByEmails(List<String> emails) {
        Query query = Query.query(Criteria.where("email").in(emails));
        return mongoOperations.find(query, Student.class);
    }

}