package com.team4.backend.controller;


import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.dto.SupervisorProfileDto;
import com.team4.backend.mapping.SupervisorMapper;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.SupervisorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody SupervisorDetailsDto supervisorDto) {
        return supervisorService.registerSupervisor(SupervisorMapper.toEntity(supervisorDto))
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")));
    }

    @PatchMapping("/addEmailToStudentList")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Mono<ResponseEntity<String>> addStudentEmailToStudentList(@RequestParam("id") String id, @RequestParam("studentEmail") String studentEmail) {
        return supervisorService.addStudentEmailToStudentList(id, studentEmail)
                .flatMap(supervisor -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));
    }

    @GetMapping("/getAssignedStudents")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER', 'SUPERVISOR')")
    public Flux<StudentDetailsDto> getAssignedStudents(@RequestParam("supervisorId") String supervisorId,
                                                       @RequestParam("semesterFullName") String semesterFullName) {
        return supervisorService.getAllAssignedStudents(supervisorId, semesterFullName);
    }

    @GetMapping("/getAllAssignedStudentsForCurrentSemester/{supervisorId}")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER', 'SUPERVISOR')")
    public Flux<StudentDetailsDto> getAllAssignedStudentsForCurrentSemester(@PathVariable String supervisorId) {
        return supervisorService.getAllAssignedStudentsForCurrentSemester(supervisorId);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<SupervisorDetailsDto> getSupervisor(@PathVariable("email") String email) {
        return supervisorService.getSupervisor(email)
                .map(SupervisorMapper::toDetailsDto);
    }

    @GetMapping("/getProfile/{email}")
    @PreAuthorize("hasAuthority('SUPERVISOR')")
    public Mono<SupervisorProfileDto> getProfile(Principal principal) {
        return supervisorService.getSupervisor(UserSessionService.getLoggedUserEmail(principal))
                .map(SupervisorMapper::toProfileDto);
    }

}