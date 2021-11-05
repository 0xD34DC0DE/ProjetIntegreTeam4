package com.team4.backend.testdata;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.enums.Categorical;
import com.team4.backend.model.enums.Expectation;
import com.team4.backend.model.enums.Rating;

import java.util.HashMap;

public abstract class EvaluationMockData {

    public static HashMap<String, Expectation> getExpectation() {
        HashMap<String, Expectation> expectation = new HashMap<>();
        expectation.put("field1", Expectation.EXCEEDS);
        expectation.put("field2", Expectation.DOES_NOT_MEET);
        expectation.put("field3", Expectation.FAR_EXCEEDS);
        return expectation;
    }

    public static HashMap<String, Categorical> getCategorical() {
        HashMap<String, Categorical> categorical = new HashMap<>();
        categorical.put("field4", Categorical.MAYBE);
        categorical.put("field5", Categorical.YES);
        return categorical;
    }

    public static HashMap<String, Rating> getRating() {
        HashMap<String, Rating> rating = new HashMap<>();
        rating.put("field6", Rating.NA);
        rating.put("field7", Rating.TOTALLY_AGREE);
        rating.put("field8", Rating.SOMEWHAT_DISAGREE);
        rating.put("field9", Rating.TOTALLY_AGREE);
        return rating;
    }

    public static HashMap<String, String> getText() {
        HashMap<String, String> text = new HashMap<>();
        text.put("field10", "value");
        text.put("studentFullName", "John Doe");
        return text;
    }

    public static Evaluation getEvaluation() {
        return Evaluation.evaluationBuilder()
                .id("evaluation_id")
                .expectation(getExpectation())
                .rating(getRating())
                .text(getText())
                .categorical(getCategorical())
                .build();
    }

    public static EvaluationDto getEvaluationDto() {
        return EvaluationDto.evaluationDtoBuilder()
                .id("evaluation_id")
                .expectation(getExpectation())
                .rating(getRating())
                .text(getText())
                .categorical(getCategorical())
                .build();
    }

}
