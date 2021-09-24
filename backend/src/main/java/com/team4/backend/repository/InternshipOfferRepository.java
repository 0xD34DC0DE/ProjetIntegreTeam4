package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InternshipOfferRepository extends ReactiveMongoRepository<InternshipOffer, String> {
}