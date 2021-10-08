package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.service.InternshipOfferService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Log
@RequestMapping("/internshipOffer")
public class InternshipOfferController {

    private final InternshipOfferService internshipOfferService;

    public InternshipOfferController(InternshipOfferService internshipOfferService) {
        this.internshipOfferService = internshipOfferService;
    }

    @PostMapping("/addAnInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','MONITOR')")
    public Mono<ResponseEntity<String>> addAnInternshipOffer(@RequestBody InternshipOfferDto internshipOfferDTO) {
        return internshipOfferService.addAnInternshipOffer(internshipOfferDTO)
                .flatMap(internshipOffer -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage())));
    }

    @GetMapping("/unvalidatedOffers")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Flux<InternshipOfferDto> getUnvalidatedInternshipOffers() {
        return internshipOfferService.getNonValidatedInternshipOffers().map(InternshipOfferMapper::toDto);
    }

    @PatchMapping("/validateInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Mono<InternshipOfferDto> validateInternshipOffer(@RequestParam("id") String id){
        return internshipOfferService.validateInternshipOffer(id).map(InternshipOfferMapper::toDto);
    }

    @PatchMapping("/refuseInternshipOffer")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Mono<InternshipOfferDto> refuseInternshipOffer(@RequestParam("id") String id){
        return internshipOfferService.refuseInternshipOffer(id).map(InternshipOfferMapper::toDto);
    }

    @GetMapping("/getNotYetValidatedInternshipOffers")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Flux<InternshipOfferDto> getNotYetValidatedInternshipOffers() {
        return internshipOfferService.getNotYetValidatedInternshipOffers().map(InternshipOfferMapper::toDto);
    }
}
