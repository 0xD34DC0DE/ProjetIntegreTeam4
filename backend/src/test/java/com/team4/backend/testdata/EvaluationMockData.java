package com.team4.backend.testdata;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.model.enums.Categorical;
import com.team4.backend.model.enums.Expectation;
import com.team4.backend.model.enums.Rating;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;

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
        text.put("supervisorFullName", "Michel Lamarck");
        text.put("date", "2021-11-09");
        return text;
    }

    public static HashMap<String, String> getText2() {
        HashMap<String, String> text = new HashMap<>();
        text.put("field10", "value");
        text.put("studentFullName", "Maxime Dupuis");
        text.put("supervisorFullName", "Maxime Dupuis");
        text.put("date", "2021-11-09");
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

    public static Evaluation getEvaluation2() {
        return Evaluation.evaluationBuilder()
                .id("evaluation_id_2")
                .expectation(getExpectation())
                .rating(getRating())
                .text(getText())
                .categorical(getCategorical())
                .build();
    }

    public static List<Evaluation> getAllList() {
        return List.of(Evaluation.evaluationBuilder()
                        .id("evaluation_id")
                        .expectation(getExpectation())
                        .rating(getRating())
                        .text(getText())
                        .categorical(getCategorical())
                        .build(),
                Evaluation.evaluationBuilder()
                        .id("evaluation_id_2")
                        .expectation(getExpectation())
                        .rating(getRating())
                        .text(getText())
                        .categorical(getCategorical())
                        .build());
    }

    public static Flux<Evaluation> getAllFlux() {
        return Flux.just(Evaluation.evaluationBuilder()
                        .id("evaluation_id")
                        .expectation(getExpectation())
                        .rating(getRating())
                        .text(getText())
                        .categorical(getCategorical())
                        .build(),
                Evaluation.evaluationBuilder()
                        .id("evaluation_id_2")
                        .expectation(getExpectation())
                        .rating(getRating())
                        .text(getText())
                        .categorical(getCategorical())
                        .build());
    }

    public static Flux<Evaluation> getAllFlux2() {
        return Flux.just(Evaluation.evaluationBuilder()
                        .id("evaluation_id")
                        .expectation(getExpectation())
                        .rating(getRating())
                        .text(getText())
                        .categorical(getCategorical())
                        .build(),
                Evaluation.evaluationBuilder()
                        .id("evaluation_id_2")
                        .expectation(getExpectation())
                        .rating(getRating())
                        .text(getText2())
                        .categorical(getCategorical())
                        .build());
    }

}
