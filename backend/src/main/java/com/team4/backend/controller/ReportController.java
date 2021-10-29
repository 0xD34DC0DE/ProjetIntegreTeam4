package com.team4.backend.controller;

import com.team4.backend.model.Student;
import com.team4.backend.service.PdfService;
import com.team4.backend.service.ReportService;
import com.team4.backend.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final StudentService studentService;

    private final ReportService reportService;

    public ReportController(StudentService studentService, ReportService reportService) {
        this.studentService = studentService;
        this.reportService = reportService;
    }

    @GetMapping(value = "/generateAllNonValidatedOffersReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateAllNonValidatedOffersReport() {
        return reportService.generateAllNonValidatedOffersReport();
    }

    @GetMapping(value = "/generateAllValidatedOffersReport", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<byte[]> generateAllValidatedOffersReport() {
        return reportService.generateAllValidatedOffersReport();
    }

    @GetMapping("/studentsWithNoCV")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<Student> getAllStudentsWithNoCV() {
        return studentService.getAllStudentsWithNoCv();
    }

    @GetMapping("/studentsWithUnvalidatedCV")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<Student> getAllStudentsWithUnvalidatedCV() {
        return studentService.getAllStudentsWithUnvalidatedCv();
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/getStudentsNoInternship")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<Student> getStudentsNoInternship() {
        return studentService.getStudentsNoInternship();
    }

    @GetMapping("/getStudentsWaitingInterview")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<Student> getStudentsWaitingInterview() {
        return studentService.getStudentsWaitingInterview();
    }

    @GetMapping("/getStudentsWaitingResponse")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<Student> getStudentsWaitingResponse() {
        return studentService.getStudentsWaitingResponse();
    }

    @GetMapping("/getStudentsWithInternship")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<Student> getStudentsWithInternship() {
        return studentService.getStudentsWithInternship();
    }

}
