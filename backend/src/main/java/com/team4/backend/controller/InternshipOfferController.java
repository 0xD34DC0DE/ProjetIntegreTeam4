package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferDTO;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.service.InternshipOfferService;
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
    public Mono<InternshipOfferDTO> addAnInternshipOffer(@RequestBody InternshipOfferDTO internshipOfferDTO){
        return internshipOfferService.addAnInternshipOffer(internshipOfferDTO);
    }
}
