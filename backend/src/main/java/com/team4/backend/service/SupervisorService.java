package com.team4.backend.service;

import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final UserService userService;

    public SupervisorService(SupervisorRepository supervisorRepository, PBKDF2Encoder pbkdf2Encoder, UserService userService) {
        this.supervisorRepository = supervisorRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userService = userService;
    }

}
