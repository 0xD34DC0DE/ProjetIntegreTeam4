package com.team4.backend.controller;

import com.team4.backend.dto.InternshipDetailsDto;
import com.team4.backend.model.Internship;
import com.team4.backend.service.InternshipService;
import com.team4.backend.testdata.InternshipMockData;
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
@WebFluxTest(value = InternshipController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class InternshipControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    InternshipService internshipService;


    @Test
    void shouldGetInternshipByStudentEmail() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();
        when(internshipService.getInternshipByEmail(internship.getStudentEmail())).thenReturn(Mono.just(internship));

        //ACT
        webTestClient
                .get()
                .uri("/internship/" + internship.getStudentEmail())
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(InternshipDetailsDto.class);
    }

    @Test
    void shouldExistsInternshipByStudentEmail() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();
        when(internshipService.existsByStudentEmail(internship.getStudentEmail())).thenReturn(Mono.just(true));

        //ACT
        webTestClient
                .get()
                .uri("/internship/exists/" + internship.getStudentEmail())
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(Boolean.class);
    }

}
