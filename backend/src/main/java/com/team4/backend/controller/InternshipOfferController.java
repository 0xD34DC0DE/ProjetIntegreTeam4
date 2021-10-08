package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.service.InternshipOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

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
    public Mono<ResponseEntity<List<InternshipOfferStudentViewDto>>> studentExclusiveInternshipOffers(
            @PathVariable("email") String studentEmail,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getStudentExclusiveOffers(studentEmail, page, size)
                .onErrorMap(
                        UserNotFoundException.class,
                        e -> new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage())
                )
                .map(InternshipOfferMapper::toStudentViewDto)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = "/studentInternshipOffers")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<List<InternshipOfferStudentViewDto>>> studentGeneralInternshipOffers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getGeneralInternshipOffers(page, size)
                .map(InternshipOfferMapper::toStudentViewDto)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = {"/pageCount/{email}"})
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<Long>> getInternshipOffersCount(
            @PathVariable("email") String studentEmail,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getInternshipOffersPageCount(studentEmail, size)
                .onErrorMap(
                        UserNotFoundException.class,
                        e -> new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage())
                )
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = "/pageCount")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<Long>> getInternshipOffersCount(
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getInternshipOffersPageCount(size)
                .map(ResponseEntity::ok);
    }

}
