package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.exception.UserDoNotExistException;
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

        when(internshipOfferService.addAnInternshipOffer(internshipOfferDTO)).thenReturn(Mono.just(internshipOfferDTO));

        //ACT
        webTestClient
                .post()
                .uri("/internshipOffer/addAnInternshipOffer")
                .bodyValue(internshipOfferDTO)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody(InternshipOfferDto.class);
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
                .expectBody(InternshipOfferDto.class);
    }

}
