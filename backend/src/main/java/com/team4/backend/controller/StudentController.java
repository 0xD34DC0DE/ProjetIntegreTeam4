package com.team4.backend.controller;


import com.team4.backend.dto.StudentCreationDto;
import com.team4.backend.dto.StudentDto;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.model.Student;
import com.team4.backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping("/getAll")
    public Flux<StudentCreationDto> getAllStudents() {
        return this.studentService.getAllStudents().map(StudentMapper::toDto);
    }

}
