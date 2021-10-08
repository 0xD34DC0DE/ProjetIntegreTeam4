package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.exception.UserDoNotExistException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.service.InternshipOfferService;
import com.team4.backend.testdata.InternshipOfferMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = InternshipOfferController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class InternshipOfferControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    InternshipOfferService internshipOfferService;

    @Test
    void shouldAddAnInternshipOffer() {
        //ARRANGE
        InternshipOfferDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.addAnInternshipOffer(internshipOfferDTO)).thenReturn(Mono.just(internshipOffer));

        //ACT
        webTestClient
                .post()
                .uri("/internshipOffer/addAnInternshipOffer")
                .bodyValue(internshipOfferDTO)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody(String.class);
    }

    @Test
    void shouldNotAddAnInternshipOffer() {
        //ARRANGE
        InternshipOfferDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();

        when(internshipOfferService.addAnInternshipOffer(internshipOfferDTO)).thenReturn(Mono.error(UserDoNotExistException::new));

        //ACT
        webTestClient
                .post()
                .uri("/internshipOffer/addAnInternshipOffer")
                .bodyValue(internshipOfferDTO)
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBody(String.class);
    }

    @Test
    void getNonValidatedIntershipOffer() {
        // ARRANGE
        Flux<InternshipOffer> internshipOfferFlux = InternshipOfferMockData.getNonValidatedInternshipOffers();
        when(internshipOfferService.getNonValidatedInternshipOffers()).thenReturn(internshipOfferFlux);

        // ACT
        webTestClient
                .get()
                .uri("/internshipOffer/unvalidatedOffers")
                .exchange()
                // ASSERT
                .expectStatus().isOk()
                .expectBodyList(InternshipOfferDto.class);
    }

    @Test
    void shouldValidateInternshipOffer() {
        //ARRANGE
        InternshipOfferDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.validateInternshipOffer(internshipOfferDTO.getId())).thenReturn(Mono.just(internshipOffer));

        //ACT
        webTestClient
                .patch()
                .uri("/internshipOffer/validateInternshipOffer?id="+internshipOfferDTO.getId())
                .bodyValue(internshipOfferDTO)
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(InternshipOfferDto.class);
    }

    @Test
    void shouldGetNotYetValidatedInternshipOffer() {
        // ARRANGE
        Flux<InternshipOffer> internshipOfferFlux = InternshipOfferMockData.getNonValidatedInternshipOffers();
        when(internshipOfferService.getNotYetValidatedInternshipOffers()).thenReturn(internshipOfferFlux);

        // ACT
        webTestClient
                .get()
                .uri("/internshipOffer/getNotYetValidatedInternshipOffers")
                .exchange()
                // ASSERT
                .expectStatus().isOk()
                .expectBodyList(InternshipOfferDto.class);
    }
    @Test
    void shouldRefuseInternshipOffer() {
        //ARRANGE
        InternshipOfferDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.refuseInternshipOffer(internshipOfferDTO.getId())).thenReturn(Mono.just(internshipOffer));

        //ACT
        webTestClient
                .patch()
                .uri("/internshipOffer/refuseInternshipOffer?id="+internshipOfferDTO.getId())
                .bodyValue(internshipOfferDTO)
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(InternshipOfferDto.class);
    }

}
