package com.team4.backend.controller;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.service.EvaluationService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'MONITOR')")
    public Mono<ResponseEntity<String>> create(@RequestBody EvaluationDto evaluationDto) {
        return evaluationService.addEvaluation(evaluationDto)
                .map(evaluation -> ResponseEntity.status(HttpStatus.CREATED).body(evaluation.getId()));
    }

    @GetMapping(value = "{evaluationId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'MONITOR')")
    public Mono<byte[]> generatePdf(@PathVariable String evaluationId) {
        return evaluationService
                .generatePdf(evaluationId);
    }

}
