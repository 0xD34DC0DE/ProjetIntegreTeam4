package com.team4.backend.controller;

import com.team4.backend.dto.MonitorDto;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.mapping.MonitorMapper;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody MonitorDto monitorDto) {
        return monitorService.registerMonitor(MonitorMapper.toEntity(monitorDto))
                .flatMap(s -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")))
                .onErrorReturn(UserAlreadyExistsException.class, ResponseEntity.status(HttpStatus.CONFLICT).body(""));
    }
}