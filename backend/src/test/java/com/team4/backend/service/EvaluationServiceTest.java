package com.team4.backend.service;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.EvaluationRepository;
import com.team4.backend.testdata.EvaluationMockData;
import com.team4.backend.testdata.StudentMockData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

}
