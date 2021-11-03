package com.team4.backend.service;

import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.InternshipManager;
import com.team4.backend.repository.InternshipManagerRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InternshipManagerService {

    private final InternshipManagerRepository internshipManagerRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    public InternshipManagerService(InternshipManagerRepository internshipManagerRepository, PBKDF2Encoder pbkdf2Encoder) {
        this.internshipManagerRepository = internshipManagerRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
    }

    public Mono<InternshipManager> register(InternshipManager internshipManager) {
        internshipManager.setPassword(pbkdf2Encoder.encode(internshipManager.getPassword()));
        return internshipManagerRepository.save(internshipManager);
    }

    public Mono<InternshipManager> findByEmail(String internshipManagerEmail) {
        return internshipManagerRepository.findByEmail(internshipManagerEmail)
                .switchIfEmpty(
                        Mono.error(
                                new UserNotFoundException("Could not find internship manager with email: " +
                                        internshipManagerEmail)
                        )
                );
    }

    public Mono<InternshipManager> findById(String internshipManagerId) {
        return internshipManagerRepository.findById(internshipManagerId).switchIfEmpty(
                Mono.error(
                        new UserNotFoundException("Could not find internship manager with id: " + internshipManagerId)
                )
        );
    }
}
