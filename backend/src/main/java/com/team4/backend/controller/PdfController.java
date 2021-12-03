package com.team4.backend.controller;

import com.team4.backend.service.PdfService;
import com.team4.backend.service.StudentService;
import org.apache.http.protocol.ResponseServer;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.RequestPartServletServerHttpRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfService pdfService;

    private final StudentService studentService;

    public PdfController(PdfService pdfService, StudentService studentService) {
        this.pdfService = pdfService;
        this.studentService = studentService;
    }

    //TODO: remove test controller method for final product
    @PreAuthorize("hasAnyAuthority('STUDENT', 'INTERNSHIP_MANAGER')")
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_PDF_VALUE)
    public Mono<byte[]> getTestPdf() {
        return Mono.just(new byte[]{0});
//        return studentService.getAll().collectList()
//                .flatMap(student -> {
//                    List<Student> studentList = student;
//                    Map<String, Object> variables = new HashMap<>();
//                    variables.put("students", studentList);
//                    return pdfService.renderPdf(new TestingStudentPdfTemplate(variables));
//                });
    }

}
