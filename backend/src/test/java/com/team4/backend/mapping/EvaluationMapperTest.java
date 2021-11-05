package com.team4.backend.mapping;

import com.team4.backend.dto.EvaluationDto;
import com.team4.backend.model.Evaluation;
import com.team4.backend.testdata.EvaluationMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluationMapperTest {

    @Test
    void mapEntityToDto() {
        //ARRANGE
        Evaluation evaluation = EvaluationMockData.getEvaluation();

        //ACT
        EvaluationDto evaluationDto = EvaluationMapper.toDto(evaluation);

        //ASSERT
        assertEquals(evaluationDto.getCategorical(), evaluation.getCategorical());
        assertEquals(evaluationDto.getRating(), evaluation.getRating());
        assertEquals(evaluationDto.getExpectation(), evaluation.getExpectation());
        assertEquals(evaluationDto.getText(), evaluation.getText());
    }

    @Test
    void mapDtoToEntity() {
        //ARRANGE
        EvaluationDto evaluationDto = EvaluationMockData.getEvaluationDto();

        //ACT
        Evaluation evaluation = EvaluationMapper.toEntity(evaluationDto);

        //ASSERT
        assertEquals(evaluation.getCategorical(), evaluationDto.getCategorical());
        assertEquals(evaluation.getRating(), evaluationDto.getRating());
        assertEquals(evaluation.getExpectation(), evaluationDto.getExpectation());
        assertEquals(evaluation.getText(), evaluationDto.getText());
    }

}
