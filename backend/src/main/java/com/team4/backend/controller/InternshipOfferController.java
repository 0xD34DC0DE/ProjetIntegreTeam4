package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.exception.DoNotExistException;
import com.team4.backend.service.InternshipOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internshipOffer")
public class InternshipOfferController {

    private final InternshipOfferService internshipOfferService;

    public InternshipOfferController(InternshipOfferService internshipOfferService) {
        this.internshipOfferService = internshipOfferService;
    }

    @PostMapping("/addAnInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','MONITOR')")
    public Mono<ResponseEntity<InternshipOfferDto>> addAnInternshipOffer(@RequestBody InternshipOfferDto internshipOfferDTO) {
        return internshipOfferService.addAnInternshipOffer(internshipOfferDTO)
                .flatMap(internshipOffer -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(internshipOffer)))
                .onErrorReturn(DoNotExistException.class, ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/unvalidatedOffers")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Flux<InternshipOfferDto> getAllNonValidatedInternshipOffer() {
        return internshipOfferService.getNonValidatedInternshipOffers();
    }

    @PatchMapping("/validateInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Mono<InternshipOfferDto> validateInternshipOffer(@RequestParam("id") String id){
        return internshipOfferService.validateInternshipOffer(id);
    }
}
