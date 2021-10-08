package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipOfferRepository extends ReactiveMongoRepository<InternshipOffer, String> {

}
