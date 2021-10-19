package com.team4.backend.controller;


import com.team4.backend.dto.StudentCreationDto;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody StudentCreationDto studentCreationDto) {
        return studentService.registerStudent(StudentMapper.toEntity(studentCreationDto))
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(error.getMessage())));
        //TODO add a non-handled exception to make sure it returns 500 and not 409
    }

    @PatchMapping("/updateStudentState")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<String>> updateStudentState(Principal principal) {
        return studentService.updateStudentState(UserSessionService.getLoggedUserEmail(principal), StudentState.INTERNSHIP_FOUND)
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage())));
    }

}
