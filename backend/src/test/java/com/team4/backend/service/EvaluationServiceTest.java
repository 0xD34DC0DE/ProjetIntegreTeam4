package com.team4.backend.service;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.repository.EvaluationRepository;
import com.team4.backend.testdata.EvaluationMockData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log
@ExtendWith(MockitoExtension.class)
public class EvaluationServiceTest {

    @Mock
    EvaluationRepository evaluationRepository;

    @InjectMocks
    EvaluationService evaluationService;

    @Test
    void shouldCreateEvaluation() {
        //ARRANGE
        Evaluation evaluation = EvaluationMockData.getEvaluation();
        EvaluationDto evaluationDto = EvaluationMockData.getEvaluationDto();
        when(evaluationRepository.save(any(Evaluation.class))).thenReturn(Mono.just(evaluation));

        //ACT
        Mono<Evaluation> evaluationMono = evaluationService.addEvaluation(evaluationDto);

        //ASSERT
        StepVerifier
                .create(evaluationMono)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

}
