package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
                        .id("618b68cae908a226e003d7e6")
                        .monitorEmail("monitor1")
                        .isExclusive(true)
                        .limitDateToApply(LocalDate.now().plusWeeks(2))
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
                        .validationDate(null).build(),
                InternshipOffer.builder()
                        .isExclusive(false)
                        .monitorEmail("monitor1")
                        .isValidated(true)
                        .limitDateToApply(LocalDate.now().minusDays(2))
                        .validationDate(LocalDateTime.now())
                        .build(),
                InternshipOffer.builder()
                        .limitDateToApply(LocalDate.now().plusWeeks(2))
                        .isExclusive(false)
                        .isValidated(true)
                        .validationDate(LocalDateTime.now()).build()
        );

        internshipOfferRepository.saveAll(internshipOffers).subscribe();
    }

    @Test
    void shouldFindAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween() {
        //ARRANGE
        LocalDateTime date1 = LocalDateTime.now();

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween(date1, date1.plusWeeks(3));


        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldCountAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween() {
        //ARRANGE
        LocalDateTime date1 = LocalDateTime.now();

        //ACT
        Mono<Long> countMono = internshipOfferRepository.countAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(date1, date1.plusWeeks(3));

        //ASSERT
        StepVerifier.create(countMono)
                .assertNext(c -> Assertions.assertEquals(1, c))
                .verifyComplete();

    }

    @Test
    void shouldFindAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween() {
        //ARRANGE
        LocalDateTime date1 = LocalDateTime.now().minusDays(3);

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferRepository.findAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(date1,
                date1.plusWeeks(3),
                PageRequest.of(0,10));


        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(){
        //ARRANGE
        String id = "618b68cae908a226e003d7e6";
        LocalDateTime date1 = LocalDateTime.now().minusDays(3);

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferRepository.findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                id,
                date1,
                date1.plusWeeks(3));


        //ASSERT
        StepVerifier.create(internshipOfferMono)
                .assertNext(internshipOffer -> Assertions.assertEquals(id,internshipOffer.getId()))
                .verifyComplete();
    }

    @Test
    void shouldFindAllByMonitorEmailAndIsValidatedTrueAndLimitDateToApplyIsBetween(){
        //ARRANGE
        String monitorEmail = "monitor1";
        LocalDateTime date1 = LocalDateTime.now().minusDays(3);

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferRepository.findAllByMonitorEmailAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                monitorEmail,
                date1,
                date1.plusWeeks(3));


        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                //TODO: fix test, result will differ if all tests are ran at the same time or individually
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindAllAllByIsValidatedFalseAndLimitDateToApplyIsBetween(){
        //ARRANGE
        LocalDateTime date1 = LocalDateTime.now();

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferRepository.findAllByIsValidatedFalseAndLimitDateToApplyIsBetween(
                date1,
                date1.plusWeeks(3));


        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindAllAllByIsValidatedTrueAndLimitDateToApplyIsBetween(){
        //ARRANGE
        LocalDateTime date1 = LocalDateTime.now();

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferRepository.findAllByIsValidatedTrueAndLimitDateToApplyIsBetween(
                date1,
                date1.plusWeeks(3));


        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

}
