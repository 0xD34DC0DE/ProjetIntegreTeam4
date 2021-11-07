package com.team4.backend.service;

import com.team4.backend.model.Semester;
import com.team4.backend.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public Flux<Semester> initializeSemestersAnnually(List<Semester> semesters) {
        return semesterRepository.saveAll(semesters);
    }

}
