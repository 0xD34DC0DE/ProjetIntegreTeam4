package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Component
public class CustomStudentRepositoryImpl implements CustomStudentRepository {

    private final ReactiveMongoOperations mongoOperations;

    public CustomStudentRepositoryImpl(ReactiveMongoOperations mongoTemplate) {
        this.mongoOperations = mongoTemplate;
    }

    @Override
    public Flux<Student> findAllByEmails(Set<String> emails) {
        Query query = Query.query(Criteria.where("email").in(emails));
        return mongoOperations.find(query, Student.class);
    }

    public Flux<Student> findAllByStudentStateAndInterviewsDateIsNotEmpty(StudentState state) {
        Query query = new Query();

        query.addCriteria(Criteria.where("studentState").is(state))
                .addCriteria(Criteria.where("interviewsDate").ne(new TreeSet<>()));

        return mongoOperations.find(query, Student.class);
    }

}