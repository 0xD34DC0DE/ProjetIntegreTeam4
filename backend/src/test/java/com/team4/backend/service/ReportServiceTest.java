package com.team4.backend.service;

import com.team4.backend.model.enums.SemesterName;
import com.team4.backend.testdata.ReportMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    PdfService pdfService;

    @Mock
    StudentService studentService;

    @Mock
    InternshipOfferService internshipOfferService;

    @Mock
    SupervisorService supervisorService;

    @InjectMocks
    ReportService reportService;

    @Test
    void shouldGenerateAllNonValidatedOffersReport() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(internshipOfferService).getAllNonValidatedOffers(any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateAllNonValidatedOffersReport(SemesterName.FALL + "-" + LocalDateTime.now().getYear());

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateAllValidatedOffersReport() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(internshipOfferService).getAllValidatedOffers(any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateAllValidatedOffersReport(SemesterName.FALL + "-" + LocalDateTime.now().getYear());

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateAllStudentsReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsFlux()).when(studentService).getAll();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateAllStudentsReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateStudentsNoCvReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsFlux()).when(studentService).getAllStudentsWithNoCv();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsNoCvReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateStudentsUnvalidatedCvReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsFlux()).when(studentService).getAllStudentsWithUnvalidatedCv();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsUnvalidatedCvReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateStudentsNoInternshipReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsFlux()).when(studentService).getStudentsNoInternship();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsNoInternshipReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateStudentsWaitingInterviewReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsFlux()).when(studentService).getStudentsWaitingInterview();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsWaitingInterviewReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void generateStudentsWithInternshipReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsFlux()).when(studentService).getStudentsWaitingResponse();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsWaitingInterviewResponseReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateStudentsWithInternshipReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsFlux()).when(studentService).getStudentsWithInternship();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsWithInternshipReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateStudentsNotEvaluatedReport() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(studentService).getAllWithNoEvaluationDateDuringSemester(any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsNotEvaluatedReport(SemesterName.FALL + "-" + LocalDateTime.now().getYear());

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateStudentsWithSupervisorWithNoCompanyEvaluation() {
        //ARRANGE
        doReturn(ReportMockData.getStudentsEmailMonoList()).when(supervisorService).getStudentsEmailWithSupervisorWithNoEvaluation(any());
        doReturn(ReportMockData.getStudentMono()).when(studentService).findByEmail(any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsWithSupervisorWithNoCompanyEvaluation(SemesterName.FALL + "-" + LocalDateTime.now().getYear());

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

}
