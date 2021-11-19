package com.team4.backend.service;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.Semester;
import com.team4.backend.model.Student;
import com.team4.backend.repository.EvaluationRepository;
import com.team4.backend.testdata.EvaluationMockData;
import com.team4.backend.testdata.SemesterMockData;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log
@ExtendWith(MockitoExtension.class)
public class EvaluationServiceTest {

    @Mock
    EvaluationRepository evaluationRepository;

    @Mock
    StudentService studentService;

    @Mock
    SemesterService semesterService;

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
    void shouldNotCreateEvaluation() {
        //ARRANGE
        Evaluation evaluation = EvaluationMockData.getEvaluation3();
        EvaluationDto evaluationDto = EvaluationMockData.getEvaluationDto();
        when(evaluationRepository.save(any(Evaluation.class))).thenReturn(Mono.just(evaluation));

        Flux<Student> students = StudentMockData.getAllStudentsFlux();
        when(studentService.getAll()).thenReturn(students);

        //ACT
        Mono<Evaluation> evaluationMono = evaluationService.addEvaluation(evaluationDto);

        //ASSERT
        StepVerifier
                .create(evaluationMono)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void shouldGetAllWithDateBetween() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);
        Flux<Evaluation> evaluationFlux = EvaluationMockData.getAllFlux();

        when(semesterService.findByFullName(any())).thenReturn(Mono.just(semester));
        when(evaluationRepository.findAll()).thenReturn(evaluationFlux);

        //ACT
        Flux<Evaluation> response = evaluationService.getAllWithDateBetween(semester.getFullName());

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldNotGetAllWithDateBetween() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);
        Flux<Evaluation> evaluationFlux = EvaluationMockData.getAllFlux();

        when(semesterService.findByFullName(any())).thenReturn(Mono.just(semester));
        when(evaluationRepository.findAll()).thenReturn(Flux.empty());

        //ACT
        Flux<Evaluation> response = evaluationService.getAllWithDateBetween(semester.getFullName());

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();
    }

}
