package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface InternshipOfferRepository extends ReactiveMongoRepository<InternshipOffer, String> {
    Flux<InternshipOffer> findAllByIsExclusiveFalseAndLimitDateToApplyAfterAndIsValidatedTrue(LocalDate date, Pageable page);

    Mono<InternshipOffer> findByIdAndIsExclusiveTrueAndLimitDateToApplyAfterAndIsValidatedTrue(String id, LocalDate date);

    Mono<Long> countAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate date);

    Mono<Long> countAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(LocalDateTime date1, LocalDateTime date2);

    Flux<InternshipOffer> findAllByMonitorEmailAndIsValidatedTrue(String monitorEmail);

    Flux<InternshipOffer> findAllByIsValidatedFalseAndLimitDateToApplyBetween(Date sessionStart, Date sessionEnd);

    Flux<InternshipOffer> findAllByIsValidatedTrueAndLimitDateToApplyBetween(Date sessionStart, Date sessionEnd);

    Flux<InternshipOffer> findAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween(LocalDateTime date1, LocalDateTime date2);
}
