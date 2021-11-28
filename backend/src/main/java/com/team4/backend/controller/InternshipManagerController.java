package com.team4.backend.controller;

import com.team4.backend.dto.InternshipManagerProfileDto;
import com.team4.backend.mapping.InternshipManagerMapper;
import com.team4.backend.service.InternshipManagerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internshipManager")
@PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
public class InternshipManagerController {

    private final InternshipManagerService internshipManagerService;

    public InternshipManagerController(InternshipManagerService internshipManagerService) {
        this.internshipManagerService = internshipManagerService;
    }

    @GetMapping("/getProfile/{email}")
    public Mono<InternshipManagerProfileDto> getProfile(@PathVariable String email) {
        return internshipManagerService.findByEmail(email)
                .map(InternshipManagerMapper::toProfileDto);
    }
}
