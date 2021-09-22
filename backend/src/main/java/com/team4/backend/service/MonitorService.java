package com.team4.backend.service;

import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;


    public Mono<Monitor> findMonitorByEmail(String email){
        return monitorRepository.findByEmailAndIsEnabledTrue(email)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"Can't find monitor with this email!")));
    }

}
