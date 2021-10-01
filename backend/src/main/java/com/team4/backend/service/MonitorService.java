package com.team4.backend.service;

import com.team4.backend.exception.DoNotExistException;
import com.team4.backend.repository.MonitorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;

    public MonitorService(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    public Mono<Boolean> existsByEmailAndIsEnabledTrue(String email) {
        return monitorRepository.existsByEmailAndIsEnabledTrue(email)
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(DoNotExistException::new));
    }
}
