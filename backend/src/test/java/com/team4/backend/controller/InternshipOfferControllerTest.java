package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferDetailsDto;
import com.team4.backend.dto.InternshipOfferStudentInterestViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.*;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.enums.SemesterName;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        InternshipOfferCreationDto internshipOfferDto = InternshipOfferMockData.getInternshipOfferCreationDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.addAnInternshipOffer(internshipOfferDto)).thenReturn(Mono.just(internshipOffer));

        //ACT
        webTestClient
                .post()
                .uri("/internshipOffer/addAnInternshipOffer")
                .bodyValue(internshipOfferDto)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody(String.class);
    }

    @Test
    void shouldNotAddAnInternshipOffer() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDto = InternshipOfferMockData.getInternshipOfferCreationDto();

        when(internshipOfferService.addAnInternshipOffer(internshipOfferDto)).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .post()
                .uri("/internshipOffer/addAnInternshipOffer")
                .bodyValue(internshipOfferDto)
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBody(String.class);
    }

    @Test
    void shouldChangeInternshipOfferExclusivity() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.changeInternshipOfferExclusivity(any(), any())).thenReturn(Mono.just(internshipOffer));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/internshipOffer/changeInternshipOfferExclusivity")
                                .queryParam("id", internshipOffer.getId())
                                .queryParam("isExclusive", true)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isNoContent();
    }

    @Test
    void shouldNotChangeInternshipOfferExclusivityWhenItIsNotFound() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.changeInternshipOfferExclusivity(any(), any())).thenReturn(Mono.error(InternshipOfferNotFoundException::new));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/internshipOffer/changeInternshipOfferExclusivity")
                                .queryParam("id", internshipOffer.getId())
                                .queryParam("isExclusive", true)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isNotFound();
    }

    @Test
    void shouldNotChangeInternshipOfferExclusivityWhenItIsNotValidated() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.changeInternshipOfferExclusivity(any(), any())).thenReturn(Mono.error(ForbiddenActionException::new));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/internshipOffer/changeInternshipOfferExclusivity")
                                .queryParam("id", internshipOffer.getId())
                                .queryParam("isExclusive", true)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isForbidden();
    }

    @Test
    void shouldAddExclusiveOfferToStudents() {
        //ARRANGE
        Set<String> emails = Set.of("test@gmail.com","test2@gmail.com");
        String id = "id_test";

        when(internshipOfferService.addExclusiveOfferToStudents(any(),any())).thenReturn(Mono.just(2L));

        //ACT
        webTestClient
                .patch()
                .uri("/internshipOffer/addExclusiveOfferToStudents/" + id)
                .bodyValue(emails)
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBody(String.class);
    }

    @Test
    void shouldValidateInternshipOffer() {
        //ARRANGE
        InternshipOfferDetailsDto internshipOfferDto = InternshipOfferMockData.getInternshipOfferDetailedDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.validateInternshipOffer(internshipOfferDto.getId(), true, "")).thenReturn(Mono.just(internshipOffer));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/internshipOffer/validateInternshipOffer")
                                .queryParam("id", internshipOfferDto.getId())
                                .queryParam("isValid", true)
                                .build())
                .bodyValue(internshipOfferDto)
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBody(String.class);
    }

    @Test
    void shouldNotValidateInternshipOffer() {
        //ARRANGE
        InternshipOfferDetailsDto internshipOfferDto = InternshipOfferMockData.getInternshipOfferDetailedDto();

        when(internshipOfferService.validateInternshipOffer(internshipOfferDto.getId(), true, "")).thenReturn(Mono.error(InternshipOfferNotFoundException::new));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/internshipOffer/validateInternshipOffer")
                                .queryParam("id", internshipOfferDto.getId())
                                .queryParam("isValid", true)
                                .build())
                .bodyValue(internshipOfferDto)
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBody(String.class);
    }

    @Test
    void shouldGetNotYetExclusiveInternshipOffers() {
        //ARRANGE
        String semesterFullName = SemesterName.WINTER + "-" + LocalDateTime.now().getYear();
        Flux<InternshipOffer> internshipOfferFlux = InternshipOfferMockData.getNonValidatedInternshipOffers();

        when(internshipOfferService.getAllValidatedOffers(semesterFullName)).thenReturn(internshipOfferFlux);

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/getAllValidatedOffers/" + semesterFullName)
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(InternshipOfferDetailsDto.class);
    }

    @Test
    void shouldGetNotYetValidatedInternshipOffer() {
        //ARRANGE
        String semesterFullName = SemesterName.WINTER + "-" + LocalDateTime.now().getYear();
        Flux<InternshipOffer> internshipOfferFlux = InternshipOfferMockData.getNonValidatedInternshipOffers();

        when(internshipOfferService.getNotYetValidatedInternshipOffers(semesterFullName)).thenReturn(internshipOfferFlux);

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/getNotYetValidatedInternshipOffers/" + semesterFullName)
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(InternshipOfferDetailsDto.class);
    }

    @Test
    void shouldReturnGeneralInternshipOffers() {
        //ARRANGE
        List<InternshipOfferStudentViewDto> internshipOffers = InternshipOfferMockData
                .getListInternshipOfferStudentViewDto(3);

        when(internshipOfferService.getGeneralInternshipOffers(any(Integer.class), any(Integer.class), any()))
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
        List<InternshipOfferStudentViewDto> internshipOffers =
                InternshipOfferMockData.getListInternshipOfferStudentViewDto(3);


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
        when(internshipOfferService.getGeneralInternshipOffers(any(Integer.class), any(Integer.class), any()))
                .thenReturn(Flux.error(InvalidPageRequestException::new));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/studentInternshipOffers")
                .exchange()
                //ASSERT
                .expectStatus().isBadRequest()
                .expectBody(String.class);
    }

    @Test
    void shouldNotReturnExclusiveInternshipOffersInvalidParametersSize() {
        //ARRANGE
        when(internshipOfferService.getStudentExclusiveOffers(
                any(String.class), any(Integer.class), any(Integer.class))
        ).thenReturn(Flux.error(InvalidPageRequestException::new));

        //ACT
        webTestClient
                .get()
                .uri("/internshipOffer/studentInternshipOffers/email@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isBadRequest()
                .expectBody(String.class);
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

    @Test
    void shouldReturnInterestedStudents() {
        //ARRANGE
        when(internshipOfferService.getInterestedStudents(any(String.class), any(String.class)))
                .thenReturn(Flux.fromIterable(InternshipOfferMockData.getListInternshipOfferStudentInterestViewDto(3)));

        //ACT
        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/internshipOffer/interestedStudents")
                                .queryParam("monitorEmail", "email@gmail.com")
                                .queryParam("semesterFullName", "FALL-2021")
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(InternshipOfferStudentInterestViewDto.class);
    }

    void shouldApplyInternshipOffer() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.applyOffer(eq(internshipOffer.getId()), any(String.class))).then(s -> {
            internshipOffer.getListEmailInterestedStudents().add("student@gmail.com");
            return Mono.just(internshipOffer);
        });

        //ACT
        webTestClient
                .patch()
                .uri("/internshipOffer/apply/" + internshipOffer.getId())
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    @Test
    void shouldNotApplyExclusiveInternshipOffer() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.applyOffer(eq(internshipOffer.getId()), any(String.class)))
                .thenReturn(Mono.error(UnauthorizedException::new));

        //ACT
        webTestClient
                .patch()
                .uri("/internshipOffer/apply/" + internshipOffer.getId())
                .exchange()
                //ASSERT
                .expectStatus().isUnauthorized();
    }

    @Test
    void shouldNotApplyInternshipOfferNonExistentOffer() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferService.applyOffer(eq(internshipOffer.getId()), any(String.class)))
                .thenReturn(Mono.error(InternshipOfferNotFoundException::new));

        //ACT
        webTestClient
                .patch()
                .uri("/internshipOffer/apply/" + internshipOffer.getId())
                .exchange()
                //ASSERT
                .expectStatus().isNotFound();
    }

}
