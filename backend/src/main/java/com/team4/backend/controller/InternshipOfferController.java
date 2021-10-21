package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferDetailedDto;
import com.team4.backend.dto.InternshipOfferStudentInterestViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.InternshipOfferService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Log
@RestController
@RequestMapping("/internshipOffer")
public class InternshipOfferController {

    private final InternshipOfferService internshipOfferService;

    public InternshipOfferController(InternshipOfferService internshipOfferService) {
        this.internshipOfferService = internshipOfferService;
    }

    @ExceptionHandler(InvalidPageRequestException.class)
    public ResponseEntity<String> onException(InvalidPageRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @PostMapping("/addAnInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','MONITOR')")
    public Mono<ResponseEntity<String>> addAnInternshipOffer(
            @RequestBody InternshipOfferCreationDto internshipOfferCreationDto) {
        return internshipOfferService.addAnInternshipOffer(internshipOfferCreationDto)
                .onErrorMap(
                        UserNotFoundException.class,
                        e -> new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage())
                )
                .map(s -> ResponseEntity.status(HttpStatus.CREATED).body(""));
    }

    @GetMapping(value = "/studentInternshipOffers/{email}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Flux<InternshipOfferStudentViewDto> studentExclusiveInternshipOffers(
            @PathVariable("email") String studentEmail,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getStudentExclusiveOffers(studentEmail, page, size);
    }

    @GetMapping(value = "/studentInternshipOffers")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Flux<InternshipOfferStudentViewDto> studentGeneralInternshipOffers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            Principal principal) {
        return internshipOfferService.getGeneralInternshipOffers(
                page,
                size,
                UserSessionService.getLoggedUserEmail(principal)
        );
    }

    @GetMapping(value = {"/pageCount/{email}"})
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<Long> getInternshipOffersCount(
            @PathVariable("email") String studentEmail,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getInternshipOffersPageCount(studentEmail, size)
                .onErrorMap(
                        UserNotFoundException.class,
                        e -> new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage())
                );
    }

    @GetMapping(value = "/pageCount")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<Long> getInternshipOffersCount(
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getInternshipOffersPageCount(size);
    }

    @PatchMapping("/validateInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Mono<ResponseEntity<String>> validateInternshipOffer(@RequestParam("id") String id,
                                                                @RequestParam("isValid") Boolean isValid) {
        return internshipOfferService.validateInternshipOffer(id, isValid)
                .flatMap(fileMetaData -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")))
                .onErrorResume(error -> Mono.just(
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage()))
                );
    }

    @GetMapping("/getNotYetValidatedInternshipOffers")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Flux<InternshipOfferDetailedDto> getNotYetValidatedInternshipOffers() {
        return internshipOfferService.getNotYetValidatedInternshipOffers().map(InternshipOfferMapper::toDto);
    }

    @GetMapping("/interestedStudents/{emailOfMonitor}")
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Flux<InternshipOfferStudentInterestViewDto> internshipOfferInterestedStudents(@PathVariable String emailOfMonitor) {
        return internshipOfferService.getInterestedStudents(emailOfMonitor);
    }

    @PatchMapping("/apply/{offerId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<String>> applyInternshipOffer(@PathVariable("offerId") String offerId,
                                                             Principal principal) {
        return internshipOfferService.applyOffer(offerId, UserSessionService.getLoggedUserEmail(principal))
                .flatMap(fileMetaData -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));
    }

}
