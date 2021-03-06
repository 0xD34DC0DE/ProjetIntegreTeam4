package com.team4.backend.service;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public Mono<Boolean> existsByEmailAndIsEnabledTrue(String email) {
        return monitorRepository.existsByEmailAndIsEnabledTrue(email);
    }

    public Mono<Monitor> registerMonitor(Monitor monitor) {
        return userService.existsByEmail(monitor.getEmail()).flatMap(exists -> {
            if (!exists) {
                monitor.setPassword(pbkdf2Encoder.encode(monitor.getPassword()));
                return monitorRepository.save(monitor);
            } else {
                return Mono.error(new UserAlreadyExistsException("User already exist!"));
            }
        });
    }

    public Mono<Monitor> findByEmail(String monitorEmail) {
        return monitorRepository.findByEmail(monitorEmail)
                .switchIfEmpty(
                        Mono.error(new UserNotFoundException("Could not find Monitor with email: " + monitorEmail))
                );
    }

    public Mono<Monitor> findById(String monitorId) {
        return monitorRepository.findById(monitorId).switchIfEmpty(
                Mono.error(new UserNotFoundException("Could not find Monitor with id: " + monitorId))
        );
    }

    public Flux<Monitor> findAllByIds(List<String> ids) {
        return monitorRepository.findAllByIds(ids);
    }

}
