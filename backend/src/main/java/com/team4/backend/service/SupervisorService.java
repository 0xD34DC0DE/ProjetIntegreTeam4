package com.team4.backend.service;

import com.team4.backend.exception.DuplicateEntryException;
import com.team4.backend.exception.InternshipOfferNotFoundException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.model.Supervisor;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Mono<Supervisor> addStudentEmailToStudentList(String supervisorId, String studentEmail){
        return supervisorRepository.findById(supervisorId)
                .flatMap(supervisor -> {
            if(!supervisor.getStudentEmails().contains(studentEmail)) {
                List<String> studentEmails = new ArrayList<>(supervisor.getStudentEmails());
                studentEmails.add(studentEmail);
                supervisor.setStudentEmails(studentEmails);
                return supervisorRepository.save(supervisor);
            }
            else {
                return Mono.error(new DuplicateEntryException("Student is already present in the supervisor's student lists"));
            }
        }).switchIfEmpty(Mono.error(new UserNotFoundException("Can't find a supervisor with given id: " + supervisorId)));
    }

    public Flux<Supervisor> getAllSupervisors(){
        return supervisorRepository.findAll();
    }
}
