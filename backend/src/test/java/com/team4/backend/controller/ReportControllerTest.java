package com.team4.backend.controller;

import com.team4.backend.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = ReportController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
class ReportControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReportService reportService;

    private final byte[] bytes = "bytes".getBytes();

    @Test
    void shouldGenerateAllNonValidatedOffersReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateAllNonValidatedOffersReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateAllNonValidatedOffersReport")
                .exchange()

        //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateAllValidatedOffersReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateAllValidatedOffersReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateAllValidatedOffersReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateAllStudentsReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateAllStudentsReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateAllStudentsReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsNoCvReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateStudentsNoCvReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsNoCvReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsUnvalidatedCvReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateStudentsUnvalidatedCvReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsUnvalidatedCvReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsNoInternshipReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateStudentsNoInternshipReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsNoInternshipReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsWaitingInterviewReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateStudentsWaitingInterviewReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsWaitingInterviewReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsWaitingInterviewResponseReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateStudentsWaitingInterviewResponseReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsWaitingInterviewResponseReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsWithInternshipReport() {
        //ARRANGE
        doReturn(Mono.just(bytes)).when(reportService).generateStudentsWithInternshipReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsWithInternshipReport")
                .exchange()

                //ASSERT
                .expectStatus().isOk();
    }
}