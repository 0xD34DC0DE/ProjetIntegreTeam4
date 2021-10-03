package com.team4.backend.controller;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.exception.FileDoNotExistException;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.service.FileMetaDataService;
import com.team4.backend.testdata.FileMetaDataMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = FileMetaDataController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class FileMetaDataControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    FileMetaDataService fileMetaDataService;


    @Test
    void countAllInvalidCvNotSeen() {
        //ARRANGE

        when(fileMetaDataService.countAllInvalidCvNotSeen()).thenReturn(Mono.just(0L));

        //ACT
        webTestClient
                .get()
                .uri("/fileMetaData/countAllInvalidCvNotSeen")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(Long.class);
    }

    @Test
    void getListInvalidCvNotSeen() {
        //ARRANGE

        Integer noPage = 0;
        when(fileMetaDataService.getListInvalidCvNotSeen(noPage)).thenReturn(Flux.just(FileMetaDataMockData.getFileMetaDataInternshipManagerViewDto()));

        //ACT

        webTestClient
                .get()
                .uri("/fileMetaData/getListInvalidCvNotSeen/" + noPage)
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(FileMetaDataInternshipManagerViewDto.class);
    }


    @Test
    void shouldValidateCv() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        when(fileMetaDataService.validateCv(fileMetaData.getId(), true)).thenReturn(Mono.just(fileMetaData));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/fileMetaData/validateCv")
                                .queryParam("id", fileMetaData.getId())
                                .queryParam("isValid", true)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(String.class);
    }

    @Test
    void shouldNotValidateCv() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        when(fileMetaDataService.validateCv(fileMetaData.getId(), true)).thenReturn(Mono.error(FileDoNotExistException::new));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/fileMetaData/validateCv")
                                .queryParam("id", fileMetaData.getId())
                                .queryParam("isValid", true)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBodyList(String.class);
    }
}
