package com.team4.backend.controller;

import com.team4.backend.dto.SemesterDto;
import com.team4.backend.service.SemesterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/semester")
@PreAuthorize("hasAnyAuthority('STUDENT','MONITOR','SUPERVISOR','INTERNSHIP_MANAGER')")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping("/getAllSemesterFullName")
    public Mono<SemesterDto> getAllSemesterFullName() {
        return semesterService.getAllSemesterFullName();
    }
}
