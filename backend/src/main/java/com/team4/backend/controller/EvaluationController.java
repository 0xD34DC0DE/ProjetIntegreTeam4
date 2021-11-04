package com.team4.backend.controller;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.service.EvaluationService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public Mono<ResponseEntity<Evaluation>> create(@RequestBody EvaluationDto evaluationDto) {
        return evaluationService.addEvaluation(evaluationDto)
                .map(evaluation -> ResponseEntity.status(HttpStatus.CREATED).body(evaluation));
    }

}
