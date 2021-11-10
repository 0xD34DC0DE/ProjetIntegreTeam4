package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {InternshipOfferRepository.class})
public class InternshipOfferRepositoryTest {

    @Autowired
    InternshipOfferRepository internshipOfferRepository;

    @BeforeAll
    void init() {
        Flux<InternshipOffer> internshipOffers = Flux.just(
                InternshipOffer.builder()
                        .isValidated(true)
                        .validationDate(LocalDateTime.now())
                        .build(),
                InternshipOffer.builder()
                        .isValidated(false)
                        .limitDateToApply(LocalDate.now().plusDays(2))
                        .validationDate(null)
                        .build(),
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now().plusWeeks(2))
                        .isValidated(false)
                        .validationDate(null).build()
        );

        internshipOfferRepository.saveAll(internshipOffers).subscribe();
    }

    @Test
    void shouldFindNotYetValidatedInternshipOffer() {
        //ACT
        Flux<InternshipOffer> validatedInternshipOffers = internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalse();

        //ASSERT
        StepVerifier.create(validatedInternshipOffers)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween(){
        //ARRANGE
        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = LocalDateTime.now().plusWeeks(3);

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween(date1,date2);


        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

}
