package com.team4.backend.service;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.exception.DuplicateEntryException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.Student;
import com.team4.backend.model.Supervisor;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    private final StudentService studentService;

    private final EvaluationService evaluationService;


    private final PBKDF2Encoder pbkdf2Encoder;

    private final UserService userService;

    public SupervisorService(SupervisorRepository supervisorRepository, PBKDF2Encoder pbkdf2Encoder, UserService userService, StudentService studentService, EvaluationService evaluationService) {
        this.supervisorRepository = supervisorRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userService = userService;
        this.studentService = studentService;
        this.evaluationService = evaluationService;
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
                    if (!supervisor.getStudentEmails().contains(studentEmail)) {
                        Set<String> studentEmails = new HashSet<>(supervisor.getStudentEmails());
                        studentEmails.add(studentEmail);
                        supervisor.setStudentEmails(studentEmails);
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
                .map(Supervisor::getStudentEmails)
                .flatMapMany(studentService::findAllByEmails)
                .map(StudentMapper::toDto);
    }

    public Mono<Supervisor> getSupervisor(String email) {
        return supervisorRepository.findSupervisorByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Can't find user with this email")));
    }

    protected Flux<Supervisor> getAll() {
        return supervisorRepository.findAllByRole("SUPERVISOR");
    }

    public Mono<List<String>> getStudentsEmailWithSupervisorWithNoEvaluation(LocalDate sessionStart, LocalDate sessionEnd) {
        AtomicReference<List<String>> reference = new AtomicReference<>();
        return getAllWithNoEvaluation(sessionStart, sessionEnd)
                .flatMap(supervisors -> {
                    reference.set(new ArrayList<>());
                    for (Supervisor supervisor : supervisors) {
                        for (String studentEmail : supervisor.getStudentEmails()) {
                            boolean emailThere = false;
                            for (String email : reference.get()) {
                                if (studentEmail.equals(email)) {
                                    emailThere = true;
                                    break;
                                }
                            }
                            if (!emailThere) {
                                reference.get().add(studentEmail);
                            }
                        }
                    }
                    return Mono.just(reference.get());
                });
    }

    public Mono<List<Supervisor>> getAllWithNoEvaluation(LocalDate sessionStart, LocalDate sessionEnd) {
        AtomicReference<List<Evaluation>> evaluations = new AtomicReference<>();
        AtomicReference<List<Supervisor>> supervisors = new AtomicReference<>(new ArrayList<>());
        return evaluationService.getAllWithDateBetween(sessionStart, sessionEnd).collectList()
                .flatMap(evaluationsList -> {
                    evaluations.set(evaluationsList);
                    return getAll().collectList();
                })
                .flatMap(supervisorsList -> {
                    for (Supervisor supervisor : supervisorsList) {
                        String fullName = supervisor.getFirstName() + " " + supervisor.getLastName();
                        boolean hasEvaluation = false;
                        for (Evaluation evaluation : evaluations.get()) {
                            if (evaluation.getText().get("supervisorFullName").equals(fullName)) {
                                hasEvaluation = true;
                                break;
                            }
                        }
                        if (!hasEvaluation) {
                            supervisors.get().add(supervisor);
                        }
                    }
                    return Mono.just(supervisors.get());
                });
    }
}
