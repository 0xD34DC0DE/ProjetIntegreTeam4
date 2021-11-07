package com.team4.backend.repository;

import com.team4.backend.model.Semester;
import com.team4.backend.model.enums.SemesterName;
import com.team4.backend.testdata.SemesterMockData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {SemesterRepository.class})
public class SemesterRepositoryTest {

    @Autowired
    SemesterRepository semesterRepository;

    @BeforeAll
    void init() {
        semesterRepository.saveAll(Flux.fromIterable(SemesterMockData.getListSemester())).subscribe();
    }

    @Test
    void shouldFindSemesterByName() {
        //ARRANGE
        String fullName = SemesterName.AUTUMN + " " + 2021;

        //ACT
        Mono<Semester> semesterMono = semesterRepository.findByFullName(fullName);

        //ASSERT
        StepVerifier.create(semesterMono)
                .assertNext(s -> assertEquals(fullName, s.getFullName()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindSemesterByName() {
        //ARRANGE
        String fullName = SemesterName.AUTUMN + " " + 2019;

        //ACT
        Mono<Semester> semesterMono = semesterRepository.findByFullName(fullName);

        //ASSERT
        StepVerifier.create(semesterMono)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldFindByFromLessThanEqualAndToGreaterThanEqual() {
        //ARRANGE
        LocalDateTime currentDate = LocalDateTime.now();

        //ACT
        Mono<Semester> semesterMono = semesterRepository.findByFromLessThanEqualAndToGreaterThanEqual(currentDate,currentDate);

        //ASSERT
        StepVerifier.create(semesterMono)
                .assertNext(s -> assertEquals(SemesterName.AUTUMN + " " + 2021,s.getFullName()))
                .verifyComplete();
    }

}
