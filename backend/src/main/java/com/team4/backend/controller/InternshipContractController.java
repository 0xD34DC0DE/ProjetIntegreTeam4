package com.team4.backend.controller;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.model.Internship;
import com.team4.backend.model.InternshipContract;
import com.team4.backend.service.InternshipContractService;
import com.team4.backend.service.InternshipManagerService;
import com.team4.backend.service.MonitorService;
import com.team4.backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
