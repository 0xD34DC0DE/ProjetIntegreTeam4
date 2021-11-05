package com.team4.backend.controller;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.service.EvaluationService;
import com.team4.backend.testdata.EvaluationMockData;
import com.team4.backend.testdata.InternshipOfferMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = EvaluationController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class EvaluationControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    EvaluationService evaluationService;

    @Test
    void shouldAddEvaluation() {
        //ARRANGE
        Evaluation evaluation = EvaluationMockData.getEvaluation();
        EvaluationDto evaluationDto = EvaluationMockData.getEvaluationDto();

        when(evaluationService.addEvaluation(evaluationDto)).thenReturn(Mono.just(evaluation));

        //ACT
        webTestClient
                .post()
                .uri("/evaluation")
                .bodyValue(evaluationDto)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody(String.class);
    }

}
