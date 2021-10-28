package com.team4.backend.controller;

import com.team4.backend.model.Student;
import com.team4.backend.pdf.StudentPdfTemplate;
import com.team4.backend.service.PdfService;
import com.team4.backend.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfService pdfService;

    private final StudentService studentService;

    public PdfController(PdfService pdfService, StudentService studentService) {
        this.pdfService = pdfService;
        this.studentService = studentService;
    }

    @PreAuthorize("hasAnyAuthority('MONITOR', 'STUDENT')")
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_PDF_VALUE)
    public Mono<byte[]> getTestPdf() {
        return studentService.findByEmail("student@gmail.com")
                .flatMap(student -> {
                    List<Student> studentList = new ArrayList<>();
                    studentList.add(student);

                    Map<String, Object> variables = new HashMap<>();
                    variables.put("students", studentList);
                    return pdfService.renderPdf(new StudentPdfTemplate(variables));
                });
    }
}
