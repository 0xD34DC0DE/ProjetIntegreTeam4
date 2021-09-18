package com.team4.backend.controller;


import com.team4.backend.dto.StudentDto;
import com.team4.backend.model.User;
import com.team4.backend.service.StudentService;
import com.team4.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    private final UserService userService;

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody StudentDto studentDto) {

        Mono<User> existingUser = userService.findByEmail(studentDto.getEmail());

        return existingUser.hasElement().map(hasElement -> {
            if (!hasElement) {
                studentService.registerStudent(StudentDto.dtoToEntity(studentDto)).subscribe().dispose();
                return ResponseEntity.status(HttpStatus.CREATED).body("");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("");
            }
        });

    }

}
