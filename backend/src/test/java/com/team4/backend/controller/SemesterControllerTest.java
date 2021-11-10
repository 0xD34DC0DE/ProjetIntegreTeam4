package com.team4.backend.controller;

import com.team4.backend.dto.SemesterDto;
import com.team4.backend.service.SemesterService;
import com.team4.backend.testdata.SemesterMockData;
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
@WebFluxTest(value = SemesterController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class SemesterControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    SemesterService semesterService;

    @Test
    void shouldGetAllSemesterFullName() {
        //ARRANGE
        when(semesterService.getAllSemesterFullName()).thenReturn(Mono.just(SemesterMockData.getSemesterDto()));

        webTestClient
                .get()
                .uri("/semester/getAllSemesterFullName")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(SemesterDto.class);
    }

}
