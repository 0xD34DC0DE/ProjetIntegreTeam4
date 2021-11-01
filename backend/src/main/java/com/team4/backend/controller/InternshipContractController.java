package com.team4.backend.controller;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.service.InternshipContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/contract")
public class InternshipContractController {

    private final InternshipContractService internshipContractService;

    public InternshipContractController(InternshipContractService internshipContractService) {
        this.internshipContractService = internshipContractService;
    }

    @PostMapping("/initiate")
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Mono<ResponseEntity<String>> initiateContract(
            @RequestBody InternshipContractCreationDto internshipContractCreationDto) {
        return internshipContractService.initiateContract(internshipContractCreationDto)
                .map(c -> ResponseEntity.status(HttpStatus.CREATED).body(""));
    }

    @GetMapping("/pdfByStudentEmail/{studentEmail}")
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Mono<byte[]> findInternshipContractByStudentEmail(
            @PathVariable("studentEmail") String studentEmail) {
        return internshipContractService.findContractByStudentEmail(studentEmail);
    }

}
