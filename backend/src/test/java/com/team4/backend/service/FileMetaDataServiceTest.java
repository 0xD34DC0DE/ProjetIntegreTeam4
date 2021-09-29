package com.team4.backend.service;

import com.team4.backend.model.FileMetaData;
import com.team4.backend.repository.FileMetaDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileMetaDataServiceTest {

    @Mock
    FileMetaDataRepository fileMetaDataRepository;

    @InjectMocks
    FileMetaDataService fileMetaDataService;

    @Test
    void countAllInvalidCvNotSeen() {
        //ARRANGE
        Mono<Long> nbrOfInvalidCvNotSeen = Mono.just(5L);

        when(fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse()).thenReturn(nbrOfInvalidCvNotSeen);

        //ACT
        Mono<Long> nbrOfInvalidCvNotSeenReturned = fileMetaDataService.countAllInvalidCvNotSeen();

        //ASSERT
        StepVerifier.create(nbrOfInvalidCvNotSeenReturned)
                .assertNext(n -> Assertions.assertEquals(5L, n))
                .verifyComplete();
    }

    @Test
    void getListInvalidCvNotSeen(){
        //ARRANGE
        when(fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse())
                .thenReturn(Flux.just(
                        FileMetaData.builder().build(),
                        FileMetaData.builder().build(),
                        FileMetaData.builder().build()
                ));

        //ACT
        Flux<FileMetaData> fileMetaDataFlux = fileMetaDataService.getListInvalidCvNotSeen();

        //ASSERT
        StepVerifier.create(fileMetaDataFlux).expectNextCount(3).verifyComplete();
    }
}
