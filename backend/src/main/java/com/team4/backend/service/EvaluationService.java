package com.team4.backend.service;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.mapping.EvaluationMapper;
import com.team4.backend.model.Evaluation;
import com.team4.backend.repository.EvaluationRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public Mono<Evaluation> addEvaluation(EvaluationDto evaluationDto) {
        return evaluationRepository.save(EvaluationMapper.toEntity(evaluationDto));
    }


}
