package com.team4.backend.controller;

import com.team4.backend.model.Student;
import com.team4.backend.service.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final StudentService studentService;

    public ReportController(StudentService studentService) {
        this.studentService = studentService;
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

    @GetMapping("/getStudentsNoOffer")
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
