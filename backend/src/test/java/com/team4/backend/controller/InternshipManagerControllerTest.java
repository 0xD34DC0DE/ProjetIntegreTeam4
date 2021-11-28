package com.team4.backend.controller;

import com.team4.backend.dto.InternshipManagerProfileDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.service.InternshipManagerService;
import com.team4.backend.testdata.InternshipManagerMockData;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = InternshipManagerController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class InternshipManagerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    InternshipManagerService internshipManagerService;

    @Test
    void shouldGetProfile() {
        //ARRANGE
        when(internshipManagerService.findByEmail(any())).thenReturn(Mono.just(InternshipManagerMockData.GetInternshipManager()));

        //ACT
        webTestClient
                .get()
                .uri("/internshipManager/getProfile")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(InternshipManagerProfileDto.class);
    }

    @Test
    void shouldNotGetProfile() {
        //ARRANGE
        when(internshipManagerService.findByEmail(any())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .get()
                .uri("/internshipManager/getProfile")
                .exchange()
                //ASSERT
                .expectStatus()
                .isNotFound()
                .expectBody(InternshipManagerProfileDto.class);
    }
}
