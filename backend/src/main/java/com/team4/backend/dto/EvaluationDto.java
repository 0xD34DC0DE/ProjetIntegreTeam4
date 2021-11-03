package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class EvaluationDto {

    private String id;

    private Map<String, String> evaluation;

    @Builder(builderMethodName = "evaluationDtoBuilder")
    public EvaluationDto(String id, Map<String, String> evaluation) {
        this.id = id;
        this.evaluation = evaluation;
    }

}
