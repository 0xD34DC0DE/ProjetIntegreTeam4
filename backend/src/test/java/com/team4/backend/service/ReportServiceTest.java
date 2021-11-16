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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    void shouldGenerateAllNonValidatedOffersReportNew() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(internshipOfferService).getAllNonValidatedOffersNew(any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateAllNonValidatedOffersReportNew(SemesterName.FALL + "-" + LocalDateTime.now().getYear());

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldGenerateAllValidatedOffersReport() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(internshipOfferService).getAllValidatedOffers(any(), any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateAllValidatedOffersReport(321);

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

    @Test
    void shouldGenerateStudentsNotEvaluatedReport() {
        //ARRANGE
        doReturn(ReportMockData.getInternshipOffers()).when(studentService).getAllWithEvaluationDateBetween(any(), any());
        doReturn(ReportMockData.getMonoBytes()).when(pdfService).renderPdf(any());

        //ACT
        Mono<byte[]> response = reportService.generateStudentsNotEvaluatedReport(321);

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(ReportMockData.getBytes()[0], s[0]);
        }).verifyComplete();
    }

    @Test
    void shouldCalculateDatesWinter() {
        //ACT
        List<Date> dates = reportService.calculateDates(121);

        //ASSERT
        assertEquals("Fri Jan 01 00:00:00 EST 2021", dates.get(0).toString());
        assertEquals("Mon May 31 00:00:00 EDT 2021", dates.get(1).toString());
    }

    @Test
    void shouldCalculateDatesSummer() {
        //ACT
        List<Date> dates = reportService.calculateDates(221);

        //ASSERT
        assertEquals("Tue Jun 01 00:00:00 EDT 2021", dates.get(0).toString());
        assertEquals("Mon Aug 30 00:00:00 EDT 2021", dates.get(1).toString());
    }

    @Test
    void shouldCalculateDatesFall() {
        //ACT
        List<Date> dates = reportService.calculateDates(321);

        //ASSERT
        assertEquals("Wed Sep 01 00:00:00 EDT 2021", dates.get(0).toString());
        assertEquals("Fri Dec 31 00:00:00 EST 2021", dates.get(1).toString());
    }

    @Test
    void shouldCalculateLocalDatesWinter() {
        //ARRANGE
        LocalDate localDate0 = LocalDate.parse("2021-01-01");
        LocalDate localDate1 = LocalDate.parse("2021-05-31");
        //ACT
        List<LocalDate> dates = reportService.calculateLocalDates(121);

        //ASSERT
        assertEquals(localDate0,dates.get(0));
        assertEquals(localDate1,dates.get(1));
    }

    @Test
    void shouldCalculateLocalDatesSummer() {
        //ARRANGE
        LocalDate localDate0 = LocalDate.parse("2021-06-01");
        LocalDate localDate1 = LocalDate.parse("2021-08-30");
        //ACT
        List<LocalDate> dates = reportService.calculateLocalDates(221);

        //ASSERT
        assertEquals(localDate0, dates.get(0));
        assertEquals(localDate1, dates.get(1));
    }

    @Test
    void shouldCalculateLocalDatesFall() {
        //ARRANGE
        LocalDate localDate0 = LocalDate.parse("2021-09-01");
        LocalDate localDate1 = LocalDate.parse("2021-12-31");
        //ACT
        List<LocalDate> dates = reportService.calculateLocalDates(321);

        //ASSERT
        assertEquals(localDate0,dates.get(0));
        assertEquals(localDate1,dates.get(1));
    }
}