package com.team4.backend.service;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.exception.DuplicateEntryException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.TimestampedEntry;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final StudentService studentService;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final UserService userService;

    public SupervisorService(SupervisorRepository supervisorRepository, PBKDF2Encoder pbkdf2Encoder, UserService userService, StudentService studentService) {
        this.supervisorRepository = supervisorRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userService = userService;
        this.studentService = studentService;
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

    //TODO : refactor studentlist for object(email,LocalDate)  to be able to fetch student by session
    public Mono<Supervisor> addStudentEmailToStudentList(String supervisorId, String studentEmail) {
        return supervisorRepository.findById(supervisorId)
                .flatMap(supervisor -> {
                    if (supervisor.getStudentTimestampedEntries().stream()
                            .noneMatch(timestampedEntry -> timestampedEntry.getEmail().equals(studentEmail))
                    ) {

                        supervisor.getStudentTimestampedEntries().add(new TimestampedEntry(studentEmail, LocalDateTime.now()));
                        return supervisorRepository.save(supervisor);
                    } else {
                        return Mono.error(new DuplicateEntryException("Student is already present in the supervisor's student lists"));
                    }
                }).switchIfEmpty(Mono.error(new UserNotFoundException("Can't find a supervisor with given id: " + supervisorId)));
    }

    //TODO : refactor to pass sessionName
    //TODO : refactor to SessionService.findByName and filter all student that has been assigned between range
    public Flux<StudentDetailsDto> getAllAssignedStudents(String supervisorId) {
        return supervisorRepository.findById(supervisorId)
                .map(Supervisor::getStudentTimestampedEntries)
                .flatMapMany(timestampedEntries -> studentService.findAllByEmails(timestampedEntries.stream()
                        .map(TimestampedEntry::getEmail)
                        .collect(Collectors.toSet())))
                .map(StudentMapper::toDto);
    }

    public Mono<Supervisor> getSupervisor(String email) {
        return supervisorRepository.findSupervisorByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Can't find user with this email")));
    }

}
