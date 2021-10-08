package com.team4.backend.service;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.Supervisor;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    public Mono<Supervisor> registerSupervisor(Supervisor supervisor) {
        return userService.existsByEmail(supervisor.getEmail()).flatMap(exists -> {
            if (!exists) {
                supervisor.setPassword(pbkdf2Encoder.encode(supervisor.getPassword()));
                return supervisorRepository.save(supervisor);
            } else {
                return Mono.error(new UserAlreadyExistsException("User already exist"));
            }
        });
    }

}
