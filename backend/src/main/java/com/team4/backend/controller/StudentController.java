package com.team4.backend.controller;


import com.team4.backend.dto.StudentDto;
import com.team4.backend.mapper.StudentMapper;
import com.team4.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final StudentMapper studentMapper;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<Void>> register(@RequestBody StudentDto studentDto) {
        ResponseEntity<Void> okResponse = new ResponseEntity<>(HttpStatus.OK);
        return studentService.registerStudent(studentMapper.dtoToEntity(studentDto)).map(s -> okResponse);
    }


}
