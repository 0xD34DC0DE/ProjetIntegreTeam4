package com.team4.backend.controller;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.exception.FileNotFoundException;
import com.team4.backend.exception.InvalidPageRequestException;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = FileMetaDataController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
class FileMetaDataControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    FileMetaDataService fileMetaDataService;

    @Test
    void shouldUploadFile() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        when(fileMetaDataService.uploadFile(any(), any(), any(), any(), any())).thenReturn(Mono.just(fileMetaData));

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("filename", fileMetaData.getFilename());
        builder.part("type", fileMetaData.getType());
        builder.part("mimeType", "application/pdf");

        builder.part("file", new ClassPathResource("application.yml"))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, HttpEntity<?>> multipartBody = builder.build();

        //ACT
        webTestClient
                .post()
                .uri("/file")
                .bodyValue(multipartBody)
                .exchange()
                //ASSERT
                .expectStatus().isCreated();
    }

    @Test
    void shouldCountAllInvalidCvNotSeen() {
        //ARRANGE

        when(fileMetaDataService.countAllInvalidCvNotSeen()).thenReturn(Mono.just(0L));

        //ACT
        webTestClient
                .get()
                .uri("/file/countAllInvalidCvNotSeen")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(Long.class);
    }

    @Test
    void shouldGetListInvalidCvNotSeen() throws InvalidPageRequestException {
        //ARRANGE
        Integer noPage = 0;
        when(fileMetaDataService.getListInvalidCvNotSeen(noPage)).thenReturn(Flux.just(FileMetaDataMockData.getFileMetaData()));

        //ACT
        webTestClient
                .get()
                .uri("/file/getListInvalidCvNotSeen/" + noPage)
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(FileMetaDataInternshipManagerViewDto.class);
    }


    @Test
    void shouldValidateCv() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        when(fileMetaDataService.validateCv(fileMetaData.getId(), true, null)).thenReturn(Mono.just(fileMetaData));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/file/validateCv")
                                .queryParam("id", fileMetaData.getId())
                                .queryParam("isValid", true)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBodyList(String.class);
    }

    @Test
    void shouldNotValidateCv() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        when(fileMetaDataService.validateCv(fileMetaData.getId(), true, null)).thenReturn(Mono.error(FileNotFoundException::new));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/file/validateCv")
                                .queryParam("id", fileMetaData.getId())
                                .queryParam("isValid", true)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBodyList(String.class);
    }

    @Test
    void shouldGetFirstValidCv() {
        //ARRANGE
        when(fileMetaDataService.getAssetIdLastWithUserEmail(any())).thenReturn(Mono.just("assetId"));

        //ACT
        webTestClient
                .get()
                .uri("/file/getLatestCv/studentEmail")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(String.class);
    }

}
