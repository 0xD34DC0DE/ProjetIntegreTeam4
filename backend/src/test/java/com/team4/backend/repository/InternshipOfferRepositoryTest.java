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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes ={InternshipOfferRepository.class})
public class InternshipOfferRepositoryTest {

    @Autowired
    InternshipOfferRepository internshipOfferRepository;

    @BeforeAll
    void init(){
        Flux<InternshipOffer> internshipOffers = Flux.just(
                InternshipOffer.builder().isValidated(true).validationDate(LocalDateTime.now()).build(),
                InternshipOffer.builder().isValidated(false).validationDate(null).build(),
                InternshipOffer.builder().isValidated(false).validationDate(null).build()
        );

        internshipOfferRepository.saveAll(internshipOffers).subscribe().dispose();
    }

    @Test
    void shouldFindUnvalidatedInternshipOffer() {
        //ACT
        Flux<InternshipOffer> validatedIntershipOffers = internshipOfferRepository.findAllInternshipOfferByIsValidatedFalse();

        //ASSERT
        StepVerifier.create(validatedIntershipOffers)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindNotYetValidatedInternshipOffer() {
        //ACT
        Flux<InternshipOffer> validatedIntershipOffers = internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalse();

        //ASSERT
        StepVerifier.create(validatedIntershipOffers)
                .assertNext(o -> assertTrue(!o.isValidated() && o.getValidationDate() == null))
                .assertNext(o -> assertTrue(!o.isValidated() && o.getValidationDate() == null))
                .verifyComplete();
    }
}
