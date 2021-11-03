package com.team4.backend.service;

import com.team4.backend.testdata.ReportMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    PdfService pdfService;

    @Mock
    StudentService studentService;

    @Mock
    InternshipOfferService internshipOfferService;

    @InjectMocks
    ReportService reportService;

    @Test
    void shouldGenerateAllNonValidatedOffersReport() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(internshipOfferService).getAllNonValidatedOffers(any(), any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> respone = reportService.generateAllNonValidatedOffersReport(119);

        //ASSERT
        StepVerifier.create(respone).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateAllValidatedOffersReport() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(internshipOfferService).getAllValidatedOffers(any(), any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateAllValidatedOffersReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateAllStudentsReport() {
        //ARRANGE
        doReturn(ReportMockData.getStudents()).when(studentService).getAll();
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
        doReturn(ReportMockData.getStudents()).when(studentService).getAllStudentsWithNoCv();
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
        doReturn(ReportMockData.getStudents()).when(studentService).getAllStudentsWithUnvalidatedCv();
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
        doReturn(ReportMockData.getStudents()).when(studentService).getStudentsNoInternship();
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
        doReturn(ReportMockData.getStudents()).when(studentService).getStudentsWaitingInterview();
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
        doReturn(ReportMockData.getStudents()).when(studentService).getStudentsWaitingResponse();
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
        doReturn(ReportMockData.getStudents()).when(studentService).getStudentsWithInternship();
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsWithInternshipReport();

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }
}