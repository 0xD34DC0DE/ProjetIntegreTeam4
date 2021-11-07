package com.team4.backend.repository;

import com.team4.backend.model.InternshipContract;
import com.team4.backend.model.User;
import com.team4.backend.service.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomInternshipContractRepositoryImpl implements CustomInternshipContractRepository {

    private final ReactiveMongoOperations mongoOperations;

    private final UserRepository userRepository;

    public CustomInternshipContractRepositoryImpl(ReactiveMongoOperations mongoOperations, UserRepository userRepository) {
        this.mongoOperations = mongoOperations;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<InternshipContract> findInternshipContractByStudentId(String studentId) {
        Query query = Query.query(Criteria.where("studentSignature.userId").is(studentId));
        return mongoOperations.findOne(query, InternshipContract.class);
    }

    @Override
    public Mono<Boolean> hasSigned(String internshipOfferId,
                                   String internshipMonitorId,
                                   String studentId,
                                   String monitorId,
                                   String userId) {

        Criteria criteria = Criteria.where("internshipOfferId")
                .is(internshipOfferId)
                .and("studentSignature.userId")
                .is(studentId)
                .and("monitorSignature.userId")
                .is(monitorId)
                .and("internshipManagerSignature.userId")
                .is(internshipMonitorId);

        return mongoOperations.findOne(Query.query(criteria), InternshipContract.class)
                .flatMap(internshipContract ->
                        userRepository.findById(userId).map(user -> {
                            switch (user.getRole()) {
                                case STUDENT:
                                    return internshipContract.getStudentSignature().getUserId().equals(userId);
                                case MONITOR:
                                    return internshipContract.getMonitorSignature().getUserId().equals(userId);
                                case INTERNSHIP_MANAGER:
                                    return internshipContract.getInternshipManagerSignature().getUserId().equals(userId);
                                default:
                                    break;
                            }
                            throw new RuntimeException("Invalid role received for hasSigned query");
                        })
                );
    }

}
