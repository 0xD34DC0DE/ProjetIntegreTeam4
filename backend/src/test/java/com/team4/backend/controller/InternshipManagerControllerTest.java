package com.team4.backend.controller;

import com.team4.backend.dto.InternshipManagerProfileDto;
import com.team4.backend.dto.SupervisorProfileDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Internship;
import com.team4.backend.model.InternshipManager;
import com.team4.backend.model.Supervisor;
import com.team4.backend.service.InternshipManagerService;
import com.team4.backend.service.MonitorService;
import com.team4.backend.testdata.InternshipManagerMockData;
import com.team4.backend.testdata.SupervisorMockData;
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
        InternshipManager internshipManager = InternshipManagerMockData.GetInternshipManager();
        when(internshipManagerService.findByEmail(internshipManager.getEmail())).thenReturn(Mono.just(internshipManager));

        //ACT
        webTestClient
                .get()
                .uri("/internshipManager/getProfile/" + internshipManager.getEmail())
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(InternshipManagerProfileDto.class);
    }

    @Test
    void shouldNotGetProfile() {
        //ARRANGE
        InternshipManager internshipManager = InternshipManagerMockData.GetInternshipManager();
        when(internshipManagerService.findByEmail(any())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .get()
                .uri("/internshipManager/getProfile/" + internshipManager.getEmail())
                .exchange()
                //ASSERT
                .expectStatus()
                .isNotFound()
                .expectBody(InternshipManagerProfileDto.class);
    }
}
