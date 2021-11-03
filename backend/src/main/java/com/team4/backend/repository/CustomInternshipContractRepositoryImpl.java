package com.team4.backend.repository;

import com.team4.backend.model.InternshipContract;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomInternshipContractRepositoryImpl implements CustomInternshipContractRepository {

    private final ReactiveMongoOperations mongoOperations;

    public CustomInternshipContractRepositoryImpl(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Mono<InternshipContract> findInternshipContractByStudentId(String studentId) {
        Query query = Query.query(Criteria.where("studentSignature.userId").is(studentId));
        return mongoOperations.findOne(query, InternshipContract.class);
    }

}
