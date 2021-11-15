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

    public EvaluationService(EvaluationRepository evaluationRepository, StudentService studentService) {
        this.evaluationRepository = evaluationRepository;
        this.studentService = studentService;
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

    public Flux<Evaluation> getAllWithDateBetween(LocalDate sessionStart, LocalDate sessionEnd) {
        return evaluationRepository.findAll()
                .flatMap(evaluation -> {
                    if (LocalDate.parse(evaluation.getText().get("date")).isAfter(sessionStart) && LocalDate.parse(evaluation.getText().get("date")).isBefore(sessionEnd)) {
                        return Flux.just(evaluation);
                    }
                    return Flux.empty();
                });
    }

}