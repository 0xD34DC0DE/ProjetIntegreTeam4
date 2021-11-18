package com.team4.backend.repository;

import com.team4.backend.model.Monitor;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CustomMonitorRepository {
    Flux<Monitor> findAllByIds(List<String> ids);
}
