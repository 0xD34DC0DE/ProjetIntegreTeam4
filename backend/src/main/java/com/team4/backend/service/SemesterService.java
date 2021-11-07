package com.team4.backend.service;

import com.team4.backend.model.Semester;
import com.team4.backend.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }


    public Mono<Semester> create(String fullName, LocalDateTime from, LocalDateTime to) {
        return semesterRepository.save(
                Semester.builder()
                        .fullName(fullName)
                        .from(from)
                        .to(to)
                        .build()
        );
    }

}
