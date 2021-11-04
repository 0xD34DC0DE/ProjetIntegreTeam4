package com.team4.backend.model;

import com.team4.backend.model.enums.Expectation;
import com.team4.backend.model.enums.Rating;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class Evaluation implements Serializable {

    @Id
    private String id;

    private Map<String, Rating> rating;

    private Map<String, Expectation> expectation;

    private Map<String, String> text;

    @Builder(builderMethodName = "evaluationBuilder")
    public Evaluation(String id, Map<String, Rating> rating, Map<String, Expectation> expectation, Map<String, String> text) {
        this.id = id;
        this.rating = rating;
        this.expectation = expectation;
        this.text = text;
    }

}
