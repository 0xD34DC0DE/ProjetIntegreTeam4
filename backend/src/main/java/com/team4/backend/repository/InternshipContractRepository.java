package com.team4.backend.repository;

import com.team4.backend.model.InternshipContract;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipContractRepository extends ReactiveMongoRepository<InternshipContract, String>,
        CustomInternshipContractRepository {
}
