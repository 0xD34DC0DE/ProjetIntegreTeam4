package com.team4.backend.repository;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {EvaluationRepository.class})
public class EvaluationRepositoryTest {

    private final EvaluationRepository evaluationRepository;

    public EvaluationRepositoryTest(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

}
