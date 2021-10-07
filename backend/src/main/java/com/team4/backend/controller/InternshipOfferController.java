package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.service.InternshipOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/internshipOffer")
public class InternshipOfferController {

    private final InternshipOfferService internshipOfferService;

    public InternshipOfferController(InternshipOfferService internshipOfferService) {
        this.internshipOfferService = internshipOfferService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> onException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
            @RequestParam(value = "size", defaultValue = "5") @Min(1) Integer size) {
        return internshipOfferService.getStudentExclusiveOffers(studentEmail, PageRequest.of(page, size))
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
            @Min(0) @RequestParam(value = "page", defaultValue = "0") Integer page,
            @Min(1) @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return internshipOfferService.getGeneralInternshipOffers(PageRequest.of(page, size))
                .map(InternshipOfferMapper::toStudentViewDto)
                .onErrorMap(
                        UserNotFoundException.class,
                        e -> new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage())
                )
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = {"/pageCount/{email}"})
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<Long>> getInternshipOffersCount(
            @PathVariable(value = "email", required = false) String studentEmail,
            @Min(1) @RequestParam(value = "size", defaultValue = "5") Integer size) {
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
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return internshipOfferService.getInternshipOffersPageCount(null, size)
                .onErrorMap(
                        UserNotFoundException.class,
                        e -> new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage())
                )
                .map(ResponseEntity::ok);
    }

}
