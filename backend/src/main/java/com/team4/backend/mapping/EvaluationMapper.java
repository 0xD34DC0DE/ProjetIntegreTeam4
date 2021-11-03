package com.team4.backend.mapping;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;


public abstract class EvaluationMapper {

    public static EvaluationDto toDto(Evaluation evaluation) {
        return EvaluationDto.evaluationDtoBuilder()
                .id(evaluation.getId())
                .evaluation(evaluation.getEvaluation())
                .build();
    }

    public static Evaluation toEntity(EvaluationDto evaluationDto) {
        return Evaluation.evaluationBuilder()
                .evaluation(evaluationDto.getEvaluation())
                .build();
    }

}
