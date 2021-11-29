package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferDetailsDto;
import com.team4.backend.dto.InternshipOfferStudentInterestViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.InternshipOfferService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
        return internshipOfferService.getInternshipOffersPageCount(studentEmail, size);
    }

    @GetMapping(value = "/pageCount")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<Long> getInternshipOffersCount(
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getInternshipOffersPageCount(size);
    }

    @PatchMapping("/makeInternshipOfferExclusive/{id}")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<ResponseEntity<String>> makeInternshipOfferExclusive(@PathVariable String id) {
        return internshipOfferService.makeInternshipOfferExclusive(id)
                .flatMap(i -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));

    }

    @PatchMapping("/validateInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Mono<ResponseEntity<String>> validateInternshipOffer(@RequestParam("id") String id,
                                                                @RequestParam("isValid") Boolean isValid) {
        return internshipOfferService.validateInternshipOffer(id, isValid)
                .flatMap(i -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));
    }

    @GetMapping("/getNotYetValidatedInternshipOffers/{semesterFullName}")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Flux<InternshipOfferDetailsDto> getNotYetValidatedInternshipOffers(@PathVariable String semesterFullName) {
        return internshipOfferService.getNotYetValidatedInternshipOffers(semesterFullName).map(InternshipOfferMapper::toDto);
    }

    @GetMapping("/getNotYetExclusiveInternshipOffers/{semesterFullName}")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<InternshipOfferDetailsDto> getNotYetExclusiveInternshipOffers(@PathVariable String semesterFullName) {
        return internshipOfferService.getNotYetExclusiveInternshipOffers(semesterFullName)
                .map(InternshipOfferMapper::toDto);
    }

    @GetMapping("/interestedStudents")
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Flux<InternshipOfferStudentInterestViewDto> internshipOfferInterestedStudents(
            @RequestParam("monitorEmail") String monitorEmail,
            @RequestParam("semesterFullName") String semesterFullName) {
        return internshipOfferService.getInterestedStudents(monitorEmail, semesterFullName);
    }

    @PatchMapping("/apply/{offerId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<String>> applyInternshipOffer(@PathVariable("offerId") String offerId,
                                                             Principal principal) {
        return internshipOfferService.applyOffer(offerId, UserSessionService.getLoggedUserEmail(principal))
                .flatMap(fileMetaData -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));
    }

}
