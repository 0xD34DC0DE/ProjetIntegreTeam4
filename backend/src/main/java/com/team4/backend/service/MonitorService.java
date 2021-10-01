package com.team4.backend.service;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;

    private final UserService userService;

    private final PBKDF2Encoder pbkdf2Encoder;

    public MonitorService(MonitorRepository monitorRepository, UserService userService, PBKDF2Encoder pbkdf2Encoder) {
        this.monitorRepository = monitorRepository;
        this.userService = userService;
        this.pbkdf2Encoder = pbkdf2Encoder;
    }

    public Mono<Monitor> findMonitorByEmail(String email) {
        return monitorRepository.findByEmail(email)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find monitor with this email.")));
    }

    public Mono<Monitor> registerMonitor(Monitor monitor) {
        return userService.existsByEmail(monitor.getEmail()).flatMap(exists -> {
            if (!exists) {
                monitor.setPassword(pbkdf2Encoder.encode(monitor.getPassword()));
                return monitorRepository.save(monitor);
            } else {
                return Mono.error(new UserAlreadyExistsException());
            }
        });
    }

}
