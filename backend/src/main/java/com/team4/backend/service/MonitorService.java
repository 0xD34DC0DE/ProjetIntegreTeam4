package com.team4.backend.service;

import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;

    public MonitorService(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    public Mono<Monitor> findMonitorByEmail(String email) {
        return monitorRepository.findByEmail(email)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find monitor with this email.")));
    }

    public Mono<Boolean> existsByEmailAndIsEnabledTrue(String email) {
        return monitorRepository.existsByEmailAndIsEnabledTrue(email)
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find monitor with this email.")));
    }
}
