package com.team4.backend.service;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.exception.DuplicateEntryException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.Semester;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.TimestampedEntry;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.util.PBKDF2Encoder;
import com.team4.backend.util.SemesterUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    private final StudentService studentService;

    private final EvaluationService evaluationService;


    private final PBKDF2Encoder pbkdf2Encoder;

    private final UserService userService;

    private final SemesterService semesterService;

    public SupervisorService(SupervisorRepository supervisorRepository,
                             PBKDF2Encoder pbkdf2Encoder,
                             UserService userService,
                             StudentService studentService,
                             SemesterService semesterService,
                             EvaluationService evaluationService) {
        this.supervisorRepository = supervisorRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userService = userService;
        this.studentService = studentService;
        this.evaluationService = evaluationService;
        this.semesterService = semesterService;
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

    public Flux<StudentDetailsDto> getAllAssignedStudents(String supervisorId, String semesterFullName) {
        return mapAssignedStudents(Mono.zip(supervisorRepository.findById(supervisorId), semesterService.findByFullName(semesterFullName))
        );
    }

    public Flux<StudentDetailsDto> getAllAssignedStudentsForCurrentSemester(String supervisorId) {
        return mapAssignedStudents(Mono.zip(supervisorRepository.findById(supervisorId), semesterService.getCurrentSemester()));
    }

    private Flux<StudentDetailsDto> mapAssignedStudents(Mono<Tuple2<Supervisor, Semester>> monoTuple) {
        return monoTuple.map(tuple -> tuple.getT1().getStudentTimestampedEntries().stream()
                        .filter(timestampedEntry -> SemesterUtil.checkIfDatesAreInsideRangeOfSemester(
                                tuple.getT2(),
                                timestampedEntry.getDate(),
                                timestampedEntry.getDate())
                        )
                        .map(TimestampedEntry::getEmail)
                        .collect(Collectors.toSet())
                ).flatMapMany(studentService::findAllByEmails)
                .map(StudentMapper::toDto);
    }

    public Mono<Supervisor> getSupervisor(String email) {
        return supervisorRepository.findSupervisorByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Can't find user with this email")));
    }

    protected Flux<Supervisor> getAll() {
        return supervisorRepository.findAllByRole("SUPERVISOR");
    }


    public Mono<List<String>> getStudentsEmailWithSupervisorWithNoEvaluation(String semesterFullName) {
        AtomicReference<List<String>> reference = new AtomicReference<>();

        return getAllWithNoEvaluation(semesterFullName)
                .flatMap(supervisors -> {
                    reference.set(new ArrayList<>());
                    supervisors.forEach(supervisor -> {
                        supervisor.getStudentTimestampedEntries().forEach(studentEmail -> {
                            AtomicBoolean emailThere = new AtomicBoolean(false);
                            reference.get().forEach(email -> {
                                if (studentEmail.getEmail().equals(email)) {
                                    emailThere.set(true);
                                }
                            });
                            if (!emailThere.get()) {
                                reference.get().add(studentEmail.getEmail());
                            }
                        });
                    });
                    return Mono.just(reference.get());
                });
    }

    public Mono<List<Supervisor>> getAllWithNoEvaluation(String semesterFullName) {
        AtomicReference<List<Evaluation>> evaluations = new AtomicReference<>();
        AtomicReference<List<Supervisor>> supervisors = new AtomicReference<>(new ArrayList<>());

        return evaluationService.getAllWithDateBetween(semesterFullName).collectList()
                .flatMap(evaluationsList -> {
                    evaluations.set(evaluationsList);
                    return getAll().collectList();
                })
                .flatMap(supervisorsList -> {
                    supervisorsList.forEach(supervisor -> {
                        String fullName = supervisor.getFirstName() + " " + supervisor.getLastName();
                        AtomicBoolean hasEvaluation = new AtomicBoolean(false);
                        evaluations.get().forEach(evaluation -> {
                            if (evaluation.getText().get("supervisorFullName").equals(fullName)) {
                                hasEvaluation.set(true);
                            }
                        });
                        if (!hasEvaluation.get()) {
                            supervisors.get().add(supervisor);
                        }
                    });
                    return Mono.just(supervisors.get());
                });
    }
}