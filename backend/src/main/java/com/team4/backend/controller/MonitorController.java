package com.team4.backend.controller;

import com.team4.backend.dto.MonitorDetailsDto;
import com.team4.backend.dto.MonitorProfileDto;
import com.team4.backend.mapping.MonitorMapper;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody MonitorDetailsDto monitorDto) {
        return monitorService.registerMonitor(MonitorMapper.toEntity(monitorDto))
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")));
    }

    @GetMapping("/getProfile/{email}")
    @PreAuthorize("hasAuthority('MONITOR')")
    public Mono<MonitorProfileDto> getProfile(Principal principal) {
        return monitorService.findByEmail(UserSessionService.getLoggedUserEmail(principal))
                .map(MonitorMapper::toProfileDto);
    }

}