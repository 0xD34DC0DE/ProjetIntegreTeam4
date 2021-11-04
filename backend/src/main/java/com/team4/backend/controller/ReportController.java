package com.team4.backend.controller;

import com.team4.backend.service.ReportService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/generateAllNonValidatedOffersReport/{session}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateAllNonValidatedOffersReport(@PathVariable("session") Integer sessionNumber) {
        return reportService.generateAllNonValidatedOffersReport(sessionNumber);
    }

    @GetMapping(value = "/generateAllValidatedOffersReport/{session}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateAllValidatedOffersReport(@PathVariable("session") Integer sessionNumber) {
        return reportService.generateAllValidatedOffersReport(sessionNumber);
    }

    @GetMapping(value = "/generateAllStudentsReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateAllStudentsReport() {
        return reportService.generateAllStudentsReport();
    }

    @GetMapping(value = "/generateStudentsNoCvReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateStudentsNoCvReport() {
        return reportService.generateStudentsNoCvReport();
    }

    @GetMapping(value = "/generateStudentsUnvalidatedCvReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateStudentsUnvalidatedCvReport() {
        return reportService.generateStudentsUnvalidatedCvReport();
    }

    @GetMapping(value = "/generateStudentsNoInternshipReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateStudentsNoInternshipReport() {
        return reportService.generateStudentsNoInternshipReport();
    }

    @GetMapping(value = "/generateStudentsWaitingInterviewReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateStudentsWaitingInterviewReport() {
        return reportService.generateStudentsWaitingInterviewReport();
    }

    @GetMapping(value = "/generateStudentsWaitingInterviewResponseReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateStudentsWaitingInterviewResponseReport() {
        return reportService.generateStudentsWaitingInterviewResponseReport();
    }

    @GetMapping(value = "/generateStudentsWithInternshipReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateStudentsWithInternshipReport() {
        return reportService.generateStudentsWithInternshipReport();
    }

    @GetMapping(value = "/generateStudentsNotEvaluatedReport/{session}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateStudentsNotEvaluatedReport(@PathVariable("session") Integer sessionNumber) {
        return reportService.generateStudentsNotEvaluatedReport(sessionNumber);
    }

}
