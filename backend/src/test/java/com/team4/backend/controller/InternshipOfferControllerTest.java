package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.UserNotFoundException;
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

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

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
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
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
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();

        when(internshipOfferService.addAnInternshipOffer(internshipOfferDTO)).thenReturn(Mono.error(UserNotFoundException::new));

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
    void shouldReturnGeneralInternshipOffers() {
        //ARRANGE
        List<InternshipOffer> internshipOffers = InternshipOfferMockData.getListInternshipOffer(3);

        when(internshipOfferService.getGeneralInternshipOffers(any(Integer.class), any(Integer.class)))
                .thenReturn(Flux.fromIterable(internshipOffers));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/studentInternshipOffers")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(InternshipOfferStudentViewDto.class);
    }

    @Test
    void shouldReturnExclusiveInternshipOffers() {
        //ARRANGE
        List<InternshipOffer> internshipOffers = InternshipOfferMockData.getListInternshipOffer(3);

        when(internshipOfferService.getStudentExclusiveOffers(
                any(String.class), any(Integer.class), any(Integer.class))
        ).thenReturn(Flux.fromIterable(internshipOffers));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/studentInternshipOffers/email@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(InternshipOfferStudentViewDto.class);
    }

    @Test
    void shouldNotReturnGeneralInternshipOffersInvalidParametersSize() {
        //ARRANGE
        when(internshipOfferService.getGeneralInternshipOffers(any(Integer.class), any(Integer.class)))
                .thenReturn(Flux.error(new ConstraintViolationException(new HashSet<>())));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/studentInternshipOffers")
                .exchange()
                //ASSERT
                .expectStatus().isBadRequest()
                .expectBody().isEmpty();
    }

    @Test
    void shouldNotReturnExclusiveInternshipOffersInvalidParametersSize() {
        //ARRANGE
        when(internshipOfferService.getStudentExclusiveOffers(
                any(String.class), any(Integer.class), any(Integer.class))
        ).thenReturn(Flux.error(new ConstraintViolationException(new HashSet<>())));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/studentInternshipOffers/email@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isBadRequest()
                .expectBody().isEmpty();
    }

    @Test
    void shouldNotReturnExclusiveInternshipOffersInvalidEmail() {
        //ARRANGE
        when(internshipOfferService.getStudentExclusiveOffers(
                any(String.class), any(Integer.class), any(Integer.class))
        ).thenReturn(Flux.error(new UserNotFoundException()));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/studentInternshipOffers/email@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturnExclusiveInternshipPageCount() {
        //ARRANGE
        when(internshipOfferService.getInternshipOffersPageCount(any(String.class), any(Integer.class))
        ).thenReturn(Mono.just(1L));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/pageCount/email@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(Long.class);
    }

    @Test
    void shouldNotReturnExclusiveInternshipPageCountInvalidEmail() {
        //ARRANGE
        when(internshipOfferService.getInternshipOffersPageCount(any(String.class), any(Integer.class))
        ).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/pageCount/email@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturnGeneralInternshipPageCount() {
        //ARRANGE
        when(internshipOfferService.getInternshipOffersPageCount(any(Integer.class))
        ).thenReturn(Mono.just(1L));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/pageCount")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(Long.class);
    }

}
