package com.team4.backend.service;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.EvaluationMapper;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.Student;
import com.team4.backend.pdf.EvaluationPdf;
import com.team4.backend.repository.EvaluationRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Log
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    private final StudentService studentService;

    private final PdfService pdfService;

    public EvaluationService(EvaluationRepository evaluationRepository, StudentService studentService, PdfService pdfService) {
        this.evaluationRepository = evaluationRepository;
        this.studentService = studentService;
        this.pdfService = pdfService;
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

    public Mono<byte[]> generatePdf(String evaluationId) {
        return getEvaluationVariables(evaluationId)
                .flatMap(variables -> pdfService.renderPdf(new EvaluationPdf(variables)));
    }

    public Mono<Map<String, Object>> getEvaluationVariables(String evaluationsId) {
        return evaluationRepository.findById(evaluationsId)
                .flatMap(evaluation -> {
                    Map<String, Object> variables = new HashMap<>();

                    variables.put("categorical", evaluation.getCategorical());
                    variables.put("expectation", evaluation.getExpectation());
                    variables.put("rating", evaluation.getRating());
                    variables.put("text", evaluation.getText());

                    return Mono.just(variables);
                });
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