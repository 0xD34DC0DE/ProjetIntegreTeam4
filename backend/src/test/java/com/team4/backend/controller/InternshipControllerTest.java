package com.team4.backend.controller;

import com.team4.backend.dto.InternshipDetailedDto;
import com.team4.backend.dto.InternshipOfferDetailedDto;
import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.model.Internship;
import com.team4.backend.model.Supervisor;
import com.team4.backend.service.InternshipOfferService;
import com.team4.backend.service.InternshipService;
import com.team4.backend.testdata.InternshipMockData;
import com.team4.backend.testdata.SupervisorMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = Internship.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class InternshipControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    InternshipService internshipService;


    @Test
    @Disabled
    void shouldGetInternshipByStudentEmail() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();
        when(internshipService.getInternshipByEmail(internship.getStudentEmail())).thenReturn(Mono.just(internship));

        //ACT
        webTestClient
                .get()
                .uri("/internship/"+internship.getStudentEmail())
                .exchange()
        //ASSERT
                .expectStatus().isOk()
                .expectBody(InternshipDetailedDto.class);
    }

    @Test
    void shouldExistsInternshipByStudentEmail() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();
        when(internshipService.existsByStudentEmail(internship.getStudentEmail())).thenReturn(Mono.just(true));

        //ACT
        webTestClient
                .get()
                .uri("/internship/"+internship.getStudentEmail())
                .exchange()
        //ASSERT
                .expectStatus().isOk()
                .expectBody(Boolean.class);
    }

}
