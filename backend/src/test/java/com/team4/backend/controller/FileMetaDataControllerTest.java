package com.team4.backend.controller;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.exception.FileDoNotExistException;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.service.FileMetaDataService;
import com.team4.backend.testdata.FileMetaDataMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = FileMetaDataController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
class FileMetaDataControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    FileMetaDataService fileMetaDataService;

    @InjectMocks
    FileMetaDataController fileMetadataController;

    @Test
    void uploadFile() {
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
    void shouldGetLoggerUserNameForRealUser() {
        //ASSERT
        Principal user = mock(Principal.class);
        when(user.getName()).thenReturn("username");

        //ACT & ASSERT
        assertEquals("username", fileMetadataController.getLoggedUserName(user));
    }

    @Test
    void shouldNotGetLoggerUserNameNoUser() {
        //ACT & ASSERT
        assertEquals("", fileMetadataController.getLoggedUserName(null));
    }

    @Test
    void countAllInvalidCvNotSeen() {
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
    void getListInvalidCvNotSeen() {
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

        when(fileMetaDataService.validateCv(fileMetaData.getId(), true)).thenReturn(Mono.just(fileMetaData));

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

        when(fileMetaDataService.validateCv(fileMetaData.getId(), true)).thenReturn(Mono.error(FileDoNotExistException::new));

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

}
