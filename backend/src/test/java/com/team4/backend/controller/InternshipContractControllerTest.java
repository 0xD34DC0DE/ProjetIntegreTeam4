package com.team4.backend.controller;

import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.InternshipContractService;
import com.team4.backend.testdata.InternshipContractMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = InternshipContractController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class InternshipContractControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private InternshipContractService internshipContractService;

    @Mock
    private UserSessionService userSessionService;

    @Test
    void shouldInitiateContract() {
        //ARRANGE
        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();

        when(internshipContractService.initiateContract(any()))
                .thenReturn(Mono.just(InternshipContractMockData.getInternshipContract()));
        //ACT
        webTestClient
                .post()
                .uri("/contract/initiate")
                .bodyValue(internshipContractDto)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    void shouldSignContract() {
        //ARRANGE
        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();

        when(internshipContractService.signContract(any(), any()))
                .thenReturn(Mono.just(InternshipContractMockData.getInternshipContract()));
        //ACT
        webTestClient
                .post()
                .uri("/contract/sign")
                .bodyValue(internshipContractDto)
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    @Test
    void shouldGetContract() {
        //ARRANGE
        when(internshipContractService.getContract(any(), any()))
                .thenReturn(Mono.just(new byte[]{0}));
        //ACT
        webTestClient
                .get()
                .uri("/contract?internshipOfferId=123&studentEmail=123@gmail.com")
                .exchange()
                //ASSERT
                .expectBody(byte[].class);
    }

    @Test
    void shouldGetContractById() {
        //ARRANGE
        when(internshipContractService.getContractById(any(), any()))
                .thenReturn(Mono.just(new byte[]{0}));

        //ACT
        webTestClient
                .get()
                .uri("/contract/byId/df0f83hnv8n")
                .exchange()
                //ASSERT
                .expectBody(byte[].class);
    }


    @Test
    void shouldGetHasSigned() {
        //ARRANGE
        when(internshipContractService.hasSigned(any(), any(), any()))
                .thenReturn(Mono.just(true));

        String url = "/contract/signed?internshipOfferId=2947fh79ew" +
                "&studentEmail=student@gmail.com&userEmail=123@gmail.com";
        //ACT
        webTestClient
                .get()
                .uri(url)
                .exchange()
                //ASSERT
                .expectBody();
    }

    @Test
    void shouldGetHasSignedById() {
        //ARRANGE
        when(internshipContractService.hasSignedByContractId(any(), any()))
                .thenReturn(Mono.just(true));

        //ACT
        webTestClient
                .get()
                .uri("/contract/signedByContractId?contractId=c497ch4wc8&userEmail=123@gmail.com")
                .exchange()
                //ASSERT
                .expectBody();
    }

}
