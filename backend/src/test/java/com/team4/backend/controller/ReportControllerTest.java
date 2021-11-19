package com.team4.backend.controller;

import com.team4.backend.model.enums.SemesterName;
import com.team4.backend.service.ReportService;
import com.team4.backend.testdata.ReportMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = ReportController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
class ReportControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReportService reportService;

    @Test
    void shouldGenerateAllNonValidatedOffersReport() {
        //ARRANGE
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateAllNonValidatedOffersReport(any());

        //ACT
        webTestClient
                .get()
                .uri("/report/generateAllNonValidatedOffersReport/" + SemesterName.FALL + "-" + LocalDateTime.now().getYear())
                .exchange()
                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateAllValidatedOffersReport() {
        //ARRANGE
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateAllValidatedOffersReport(any());

        //ACT
        webTestClient
                .get()
                .uri("/report/generateAllValidatedOffersReport/" + SemesterName.FALL + "-" + LocalDateTime.now().getYear())
                .exchange()
                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateAllStudentsReport() {
        //ARRANGE
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateAllStudentsReport();

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
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsNoCvReport();

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
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsUnvalidatedCvReport();

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
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsNoInternshipReport();

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
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsWaitingInterviewReport();

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
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsWaitingInterviewResponseReport();

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
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsWithInternshipReport();

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsWithInternshipReport")
                .exchange()
                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsNotEvaluatedReport() {
        //ARRANGE
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsNotEvaluatedReport(any());

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsNotEvaluatedReport/" + SemesterName.FALL + "-" + LocalDateTime.now().getYear())
                .exchange()
                //ASSERT
                .expectStatus().isOk();
    }

    @Test
    void shouldGenerateStudentsWithSupervisorWithNoCompanyEvaluation() {
        //ARRANGE
        doReturn(ReportMockData.getMonoBytes()).when(reportService).generateStudentsWithSupervisorWithNoCompanyEvaluation(any());

        //ACT
        webTestClient
                .get()
                .uri("/report/generateStudentsWithSupervisorWithNoCompanyEvaluation/" + SemesterName.FALL + "-" + LocalDateTime.now().getYear())
                .exchange()
                //ASSERT
                .expectStatus().isOk();
    }

}