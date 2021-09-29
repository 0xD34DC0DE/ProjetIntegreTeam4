package com.team4.backend.controller;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.service.FileMetaDataService;
import com.team4.backend.testdata.InternshipOfferMockData;
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

import java.util.List;

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

        when(fileMetaDataService.getListInvalidCvNotSeen()).thenReturn(Flux.just(FileMetaData.builder().build()));

        //ACT
        webTestClient
                .get()
                .uri("/fileMetaData/getListInvalidCvNotSeen")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(FileMetaData.class);
    }
}
