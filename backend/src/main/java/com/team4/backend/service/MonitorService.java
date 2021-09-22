package com.team4.backend.service;

import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;


    public Mono<Monitor> findMonitorByEmail(String email) {
        return monitorRepository.findByEmailAndIsEnabledTrue(email)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find monitor with this email!")));
    }

}
