package com.team4.backend.controller;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.service.InternshipContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Mono<byte[]> getContract(@RequestParam("internshipOfferId") String internshipOfferId,
                                    @RequestParam("studentEmail") String studentEmail) {
        return internshipContractService.getContract(internshipOfferId, studentEmail);
    }

    @GetMapping("/signed")
    @PreAuthorize("hasAnyAuthority('MONITOR', 'STUDENT', 'INTERNSHIP_MANAGER')")
    public Mono<Boolean> hasSigned(@RequestParam("internshipOfferId") String internshipOfferId,
                                   @RequestParam("studentEmail") String studentEmail,
                                   @RequestParam("userEmail") String userEmail) {
        return internshipContractService.hasSigned(internshipOfferId, studentEmail, userEmail);
    }

    @GetMapping("/t")
    @PreAuthorize("hasAnyAuthority('MONITOR', 'STUDENT', 'INTERNSHIP_MANAGER')")
    public Mono<Boolean> getInternshipContractsTwoWeeksLeft(){
        internshipContractService.getInternshipContractsTwoWeeksLeft();
        return Mono.just(true);
    }


}
