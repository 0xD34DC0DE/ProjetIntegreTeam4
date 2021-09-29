package com.team4.backend.repository;

import com.team4.backend.model.FileMetaData;
import com.team4.backend.model.Monitor;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
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

@Log
@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {FileMetaDataRepository.class})
public class FileMetaDataRepositoryTest {

    @Autowired
    FileMetaDataRepository fileMetaDataRepository;

    @BeforeAll
    void init() {

        Flux<FileMetaData> fileMetaDataFlux = Flux.just(
                FileMetaData.builder().build(),
                FileMetaData.builder().build(),
                FileMetaData.builder().build(),
                FileMetaData.builder().build()
        );


        fileMetaDataRepository.saveAll(fileMetaDataFlux).subscribe();
    }

    @Test
    void countAllByIsValidFalseAndIsSeenFalse() {
        //ACT
        Mono<Long> nbrOfFileInvalidAndNotSeen = fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse();

        //ASSERT
        StepVerifier.create(nbrOfFileInvalidAndNotSeen)
                .assertNext(n -> Assertions.assertEquals(4L, n))
                .verifyComplete();
    }

    @Test
    void findAllByIsValidFalseAndIsSeenFalse() {
        //ACT
        Flux<FileMetaData> fileMetaDataFlux = fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse();

        //ASSERT
        StepVerifier.create(fileMetaDataFlux).expectNextCount(4).verifyComplete();
    }
}
