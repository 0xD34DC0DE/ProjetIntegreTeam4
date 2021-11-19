package com.team4.backend.repository;

import com.team4.backend.model.FileMetaData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                FileMetaData.builder().isValid(false).isSeen(false).build(),
                FileMetaData.builder().isValid(false).isSeen(false).build(),
                FileMetaData.builder().isValid(false).isSeen(true).build(),
                FileMetaData.builder().isValid(true).isSeen(true).build(),
                FileMetaData.builder().userEmail("test_user_email@gmail.com").isValid(false).isSeen(true).build(),
                FileMetaData.builder().userEmail("test_user_email@gmail.com").isValid(false).isSeen(true).build()
        );

        fileMetaDataRepository.saveAll(fileMetaDataFlux).subscribe();
    }

    @Test
    void countAllByIsValidFalseAndIsSeenFalse() {
        //ACT
        Mono<Long> nbrOfFileInvalidAndNotSeen = fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse();

        //ASSERT
        StepVerifier.create(nbrOfFileInvalidAndNotSeen)
                .assertNext(n -> assertEquals(2L, n))
                .verifyComplete();
    }

    @Test
    void findAllByIsValidFalseAndIsSeenFalse() {
        //ACT
        Flux<FileMetaData> fileMetaDataFlux = fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse(PageRequest.of(0, 5, Sort.by("uploadDate")));

        //ASSERT
        StepVerifier.create(fileMetaDataFlux).expectNextCount(2).verifyComplete();
    }

    @Test
    void findAllCvByUserEmail(){
        //ACT
        String userEmail = "test_user_email@gmail.com";
        Flux<FileMetaData> fileMetaDataFlux = fileMetaDataRepository.findAllByUserEmail(userEmail);

        //ASSERT
        StepVerifier
                .create(fileMetaDataFlux)
                .assertNext(f -> assertEquals(userEmail, f.getUserEmail()))
                .assertNext(f -> assertEquals(userEmail, f.getUserEmail()))
                .verifyComplete();
    }

}
