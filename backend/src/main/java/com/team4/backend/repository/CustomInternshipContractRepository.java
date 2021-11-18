package com.team4.backend.repository;


import com.team4.backend.model.InternshipContract;
import reactor.core.publisher.Mono;

public interface CustomInternshipContractRepository {

    Mono<InternshipContract> findInternshipContractByStudentId(String studentId);

    Mono<Boolean> hasSigned(String internshipOfferId,
                            String internshipMonitorId,
                            String studentId,
                            String monitorId,
                            String userId);
    
}
