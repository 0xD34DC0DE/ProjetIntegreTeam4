package com.team4.backend.controller;


import com.team4.backend.dto.StudentDto;
import com.team4.backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public Mono<ServerResponse> register(@RequestBody StudentDto studentDto) {
        return Mono.just( StudentDto.dtoToEntity(studentDto)) studentService.findByEmail(studentDto.getEmail())
                .switchIfEmpty(studentService.registerStudent(StudentDto.dtoToEntity(studentDto))).then();

    }

}
