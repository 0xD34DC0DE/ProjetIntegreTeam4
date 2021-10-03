package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.service.InternshipOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Mono<ResponseEntity<String>> addAnInternshipOffer(@RequestBody InternshipOfferDto internshipOfferDTO) {
        return internshipOfferService.addAnInternshipOffer(internshipOfferDTO)
                .flatMap(internshipOffer -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage())));
    }
}
