package com.team4.backend.dto;

import com.team4.backend.model.enums.Categorical;
import com.team4.backend.model.enums.Expectation;
import com.team4.backend.model.enums.Rating;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class EvaluationDto implements Serializable {

    private String id;

    private Map<String, Rating> rating;

    private Map<String, Expectation> expectation;

    private Map<String, Categorical> categorical;

    private Map<String, String> text;

    @Builder(builderMethodName = "evaluationDtoBuilder")
    public EvaluationDto(String id, Map<String, Rating> rating, Map<String, Expectation> expectation, Map<String, String> text, Map<String, Categorical> categorical) {
        this.id = id;
        this.rating = rating;
        this.expectation = expectation;
        this.categorical = categorical;
        this.text = text;
    }

}
