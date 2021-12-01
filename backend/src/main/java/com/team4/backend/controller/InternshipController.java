package com.team4.backend.controller;

import com.team4.backend.dto.InternshipCreationDto;
import com.team4.backend.dto.InternshipDetailsDto;
import com.team4.backend.mapping.InternshipMapper;
import com.team4.backend.service.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internship")
public class InternshipController {

    private final InternshipService internshipService;

    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @PostMapping()
    public Mono<ResponseEntity<String>> createInternship(@RequestBody InternshipCreationDto internshipCreationDto) {
        return internshipService.createInternship(internshipCreationDto)
                .map(offer -> ResponseEntity.status(HttpStatus.CREATED).body(""));
    }

    @GetMapping("/{studentEmail}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<InternshipDetailsDto> getInternshipByStudentEmail(@PathVariable("studentEmail") String studentEmail) {
        return internshipService.getInternshipByEmail(studentEmail)
                .map(InternshipMapper::toDetailedDto);
    }

    @GetMapping("/exists/{studentEmail}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Boolean> existsByStudentEmail(@PathVariable("studentEmail") String studentEmail){
        return internshipService.existsByStudentEmail(studentEmail);
    }
}
