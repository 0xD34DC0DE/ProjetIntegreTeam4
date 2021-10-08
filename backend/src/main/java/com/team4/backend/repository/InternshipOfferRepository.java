package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface InternshipOfferRepository extends ReactiveMongoRepository<InternshipOffer, String> {
    Flux<InternshipOffer> findAllByIsExclusiveFalseAndLimitDateToApplyAfterAndIsValidatedTrue(LocalDate date, Pageable page);

    Mono<InternshipOffer> findByIdAndIsExclusiveTrueAndLimitDateToApplyAfterAndIsValidatedTrue(String id, LocalDate date);

    Mono<Long> countAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate date);
    Flux<InternshipOffer> findAllInternshipOfferByIsValidatedFalse();
    Flux<InternshipOffer> findAllByValidationDateNullAndIsValidatedFalse();
}
