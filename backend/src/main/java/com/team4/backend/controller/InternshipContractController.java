package com.team4.backend.controller;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.InternshipContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/contract")
public class InternshipContractController {

    private final InternshipContractService internshipContractService;

    public InternshipContractController(InternshipContractService internshipContractService) {
        this.internshipContractService = internshipContractService;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> onException(UserNotFoundException e) {
        System.err.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @PostMapping("/initiate")
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Mono<ResponseEntity<String>> initiateContract(
            @RequestBody InternshipContractCreationDto internshipContractCreationDto) {
        return internshipContractService.initiateContract(internshipContractCreationDto)
                .map(c -> ResponseEntity.status(HttpStatus.CREATED).body(""));
    }

    @PostMapping("/sign")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'INTERNSHIP_MANAGER')")
    public Mono<ResponseEntity<String>> signContract(
            @RequestBody InternshipContractDto internshipContractDto,
            Principal principal) {
        return internshipContractService
                .signContract(internshipContractDto,
                        UserSessionService.getLoggedUserEmail(principal)
                )
                .map(c -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(""));
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Mono<byte[]> getContract(@RequestParam("internshipOfferId") String internshipOfferId,
                                    @RequestParam("studentEmail") String studentEmail) {
        return internshipContractService.getContract(internshipOfferId, studentEmail);
    }

    @GetMapping(path = "/byId/{contractId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER', 'STUDENT')")
    public Mono<byte[]> getContractById(@PathVariable("contractId") String contractId, Principal principal) {
        return internshipContractService.getContractById(contractId, UserSessionService.getLoggedUserEmail(principal));
    }

    @GetMapping("/signed")
    @PreAuthorize("hasAnyAuthority('MONITOR', 'STUDENT', 'INTERNSHIP_MANAGER')")
    public Mono<Boolean> hasSigned(@RequestParam("internshipOfferId") String internshipOfferId,
                                   @RequestParam("studentEmail") String studentEmail,
                                   @RequestParam("userEmail") String userEmail) {
        return internshipContractService.hasSigned(internshipOfferId, studentEmail, userEmail);
    }

    @GetMapping("/signedByContractId")
    @PreAuthorize("hasAnyAuthority('MONITOR', 'STUDENT', 'INTERNSHIP_MANAGER')")
    public Mono<Boolean> hasSignedByContractId(@RequestParam("contractId") String contractId,
                                   @RequestParam("userEmail") String userEmail) {
        return internshipContractService.hasSignedByContractId(contractId, userEmail);
    }

}
