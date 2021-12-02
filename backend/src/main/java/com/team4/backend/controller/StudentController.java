package com.team4.backend.controller;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.dto.StudentProfileDto;
import com.team4.backend.dto.UserDto;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.StudentService;
import com.team4.backend.service.SupervisorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    private final SupervisorService supervisorService;

    public StudentController(StudentService studentService, SupervisorService supervisorService) {
        this.studentService = studentService;
        this.supervisorService = supervisorService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody StudentDetailsDto studentCreationDto) {
        return studentService.registerStudent(StudentMapper.toEntity(studentCreationDto))
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")));
    }

    @PatchMapping("/updateStudentState")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<String>> updateStudentState(Principal principal) {
        return studentService.updateStudentState(UserSessionService.getLoggedUserEmail(principal), StudentState.INTERNSHIP_FOUND)
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));
    }

    @PatchMapping("/updateInterviewDate/{interviewDate}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<String>> updateInterviewDate(Principal principal, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate interviewDate) {
        return studentService.updateInterviewDate(UserSessionService.getLoggedUserEmail(principal), interviewDate)
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));
    }

    @GetMapping("/getProfile")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<StudentProfileDto> getStudentProfile(Principal principal) {
        return studentService.findByEmail(UserSessionService.getLoggedUserEmail(principal))
                .map(StudentMapper::toProfileDto);
    }

    @GetMapping("/getAllStudentsNoSupervisor")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<UserDto> getAllStudentsNoSupervisor() {
        return supervisorService.getAllStudentsNoSupervisor();
    }
    
    @GetMapping("/getAllStudentNotContainingExclusiveOffer/{offerId}")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<StudentDetailsDto> getAllStudentNotContainingExclusiveOffer(@PathVariable String offerId) {
        return studentService.getAllStudentNotContainingExclusiveOffer(offerId)
                .map(StudentMapper::toDto);
    }

    @GetMapping("/hasValidCv/{studentEmail}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<Boolean> hasValidCv(@PathVariable("studentEmail") String studentEmail) {
        return studentService.hasValidCv(studentEmail);
    }

}
