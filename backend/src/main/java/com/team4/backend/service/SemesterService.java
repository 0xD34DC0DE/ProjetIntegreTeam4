package com.team4.backend.service;

import com.team4.backend.repository.SemesterRepository;
import org.springframework.stereotype.Service;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }


}
