package com.team4.backend.repository;

import com.team4.backend.model.InternshipContract;
import com.team4.backend.model.User;
import com.team4.backend.service.UserService;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomInternshipContractRepositoryImpl implements CustomInternshipContractRepository {

    private final ReactiveMongoOperations mongoOperations;

    private final UserService userService;

    public CustomInternshipContractRepositoryImpl(ReactiveMongoOperations mongoOperations, UserService userService) {
        this.mongoOperations = mongoOperations;
        this.userService = userService;
    }

    @Override
    public Mono<InternshipContract> findInternshipContractByStudentId(String studentId) {
        Query query = Query.query(Criteria.where("studentSignature.userId").is(studentId));
        return mongoOperations.findOne(query, InternshipContract.class);
    }

    @Override
    public Mono<Boolean> hasSigned(String internshipOfferId, String userId) {
        Criteria criteria = Criteria.where("internshipOfferId").is(internshipOfferId);
        return userService.findById(userId).map(user -> {
            switch (user.getRole()) {
                case STUDENT:
                    return criteria.and("studentSignature.userId").is(userId);
                case MONITOR:
                    return criteria.and("monitorSignature.userId").is(userId);
                case INTERNSHIP_MANAGER:
                    return criteria.and("internshipManagerSignature.userId").is(userId);
                default:
                   break;
            }
            throw new RuntimeException("Invalid role received for hasSigned query");
        }).flatMap(c -> {
            Query query = Query.query(c);
            return mongoOperations.findOne(query, InternshipContract.class).hasElement();
        });
    }

}
