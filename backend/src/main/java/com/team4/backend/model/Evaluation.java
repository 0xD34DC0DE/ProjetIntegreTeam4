package com.team4.backend.model;

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

    private Map<String, String> evaluation;

    @Builder(builderMethodName = "evaluationBuilder")
    public Evaluation(String id, Map<String, String> evaluation) {
        this.id = id;
        this.evaluation = evaluation;
    }

}
