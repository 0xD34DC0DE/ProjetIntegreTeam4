package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface InternshipOfferRepository extends ReactiveMongoRepository<InternshipOffer, String> {

    Flux<InternshipOffer> findAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(LocalDateTime date1, LocalDateTime date2, Pageable pageable);

    Mono<InternshipOffer> findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(String id, LocalDateTime date1, LocalDateTime date2);

    Mono<Long> countAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(LocalDateTime date1, LocalDateTime date2);

    Flux<InternshipOffer> findAllByMonitorEmailAndIsValidatedTrue(String monitorEmail);

    Flux<InternshipOffer> findAllByMonitorEmailAndIsValidatedTrueAndLimitDateToApplyIsBetween(String monitorEmail, LocalDateTime date1, LocalDateTime date2);

    Flux<InternshipOffer> findAllByIsValidatedFalseAndLimitDateToApplyIsBetween(LocalDateTime date1,LocalDateTime date2);

    Flux<InternshipOffer> findAllByIsValidatedTrueAndLimitDateToApplyIsBetween(LocalDateTime date1, LocalDateTime date2);

    Flux<InternshipOffer> findAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween(LocalDateTime date1, LocalDateTime date2);
}
