package com.team4.backend.model;

import com.team4.backend.model.enums.Categorical;
import com.team4.backend.model.enums.Expectation;
import com.team4.backend.model.enums.Rating;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "evaluations")
public class Evaluation implements Serializable {

    @Id
    private String id;

    private Map<String, Rating> rating;

    private Map<String, Expectation> expectation;

    private Map<String, Categorical> categorical;

    private Map<String, String> text;

    @Builder(builderMethodName = "evaluationBuilder")
    public Evaluation(String id, Map<String, Rating> rating, Map<String, Expectation> expectation, Map<String, String> text, Map<String, Categorical> categorical) {
        this.id = id;
        this.rating = rating;
        this.expectation = expectation;
        this.categorical = categorical;
        this.text = text;
    }

}
