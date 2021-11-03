package com.team4.backend.controller;

import com.team4.backend.dto.InternshipCreationDto;
import com.team4.backend.service.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internship")
public class InternshipController {

    private final InternshipService internshipService;

    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @PostMapping()
    public Mono<ResponseEntity<String>> createInternship(@RequestBody InternshipCreationDto internshipCreationDto) {
        return internshipService.createInternship(internshipCreationDto)
                .map(offer -> ResponseEntity.status(HttpStatus.CREATED).body(""));
    }
}
