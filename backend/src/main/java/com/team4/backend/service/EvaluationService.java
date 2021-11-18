package com.team4.backend.service;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.EvaluationMapper;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.Student;
import com.team4.backend.repository.EvaluationRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@Log
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    private final StudentService studentService;

    private final SemesterService semesterService;

    public EvaluationService(EvaluationRepository evaluationRepository,
                             StudentService studentService,
                             SemesterService semesterService) {
        this.evaluationRepository = evaluationRepository;
        this.studentService = studentService;
        this.semesterService = semesterService;
    }

    public Mono<Evaluation> addEvaluation(EvaluationDto evaluationDto) {
        AtomicReference<String> fullName = new AtomicReference<>("");
        AtomicReference<Evaluation> atomicReference = new AtomicReference<>();
        return evaluationRepository.save(EvaluationMapper.toEntity(evaluationDto))
                .flatMap(evaluation -> {
                    atomicReference.set(evaluation);
                    fullName.set(evaluation.getText().get("studentFullName"));
                    return studentService.getAll().collectList();
                }).flatMap(students -> {
                    for (Student student : students) {
                        if ((student.getFirstName() + " " + student.getLastName()).equals(fullName.toString())) {
                            student.getEvaluationsDates().add(LocalDate.now());
                            return studentService.save(student);
                        }
                    }
                    return Mono.error(new UserNotFoundException("No student found with the name : " + fullName.toString()));
                }).flatMap(s -> Mono.just(atomicReference.get()));
    }

    public Flux<Evaluation> getAllWithDateBetween(String semesterFullName) {
        return semesterService.findByFullName(semesterFullName)
                .flatMapMany(semester -> evaluationRepository.findAll()
                        .filter(evaluation -> LocalDate.parse(evaluation.getText().get("date")).atStartOfDay().isAfter(semester.getFrom()) &&
                                LocalDate.parse(evaluation.getText().get("date")).atStartOfDay().isBefore(semester.getTo())
                        )

                );
    }

}