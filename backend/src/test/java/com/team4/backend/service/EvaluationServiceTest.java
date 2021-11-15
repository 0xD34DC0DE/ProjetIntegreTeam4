package com.team4.backend.service;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.Student;
import com.team4.backend.repository.EvaluationRepository;
import com.team4.backend.testdata.EvaluationMockData;
import com.team4.backend.testdata.StudentMockData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log
@ExtendWith(MockitoExtension.class)
public class EvaluationServiceTest {

    @Mock
    EvaluationRepository evaluationRepository;

    @Mock
    StudentService studentService;

    @InjectMocks
    EvaluationService evaluationService;

    @Test
    void shouldCreateEvaluation() {
        //ARRANGE
        Evaluation evaluation = EvaluationMockData.getEvaluation();
        EvaluationDto evaluationDto = EvaluationMockData.getEvaluationDto();
        when(evaluationRepository.save(any(Evaluation.class))).thenReturn(Mono.just(evaluation));

        Flux<Student> students = StudentMockData.getAllStudentsFlux();
        when(studentService.getAll()).thenReturn(students);

        Student student = StudentMockData.getMockStudent();
        when(studentService.save(any())).thenReturn(Mono.just(student));

        //ACT
        Mono<Evaluation> evaluationMono = evaluationService.addEvaluation(evaluationDto);

        //ASSERT
        StepVerifier
                .create(evaluationMono)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    void shouldGetAllWithDateBetween() {
        //ARRANGE
        Flux<Evaluation> evaluationFlux  = EvaluationMockData.getAllFlux();
        when(evaluationRepository.findAll()).thenReturn(evaluationFlux);

        //ACT
        Flux<Evaluation> response = evaluationService.getAllWithDateBetween(LocalDate.of(2021, 9, 1), LocalDate.of(2021, 12, 31));

        //ASSERT
        StepVerifier.create(response).assertNext(r1 -> {
            assertEquals(EvaluationMockData.getEvaluation(), r1);
        }).assertNext(r2 -> {
            assertEquals(EvaluationMockData.getEvaluation2(), r2);
        }).verifyComplete();
    }

    @Test
    void shouldNotGetAllWithDateBetween() {
        //ARRANGE
        Flux<Evaluation> evaluationFlux  = EvaluationMockData.getAllFlux();
        when(evaluationRepository.findAll()).thenReturn(evaluationFlux);

        //ACT
        Flux<Evaluation> response = evaluationService.getAllWithDateBetween(LocalDate.of(2020, 9, 1), LocalDate.of(2020, 12, 31));

        //ASSERT
        StepVerifier.create(response).verifyComplete();
    }

}
