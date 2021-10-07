package com.team4.backend.controller;

import com.team4.backend.service.FileMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import static org.mockito.Mockito.*;


@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = FileMetadataController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
class FileMetadataControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    FileMetadataService fileMetadataService;

    @InjectMocks
    FileMetadataController fileMetadataController;

    private Mono<ResponseEntity<Void>> responseEntityMono;

    @Test
    void uploadFile() throws URISyntaxException {
        //ARRANGE
        responseEntityMono = Mono.just(ResponseEntity.created(new URI("location")).build());

        String filename = "filename";
        String type = "CV";
        String mimeType = "application/pdf";
        when(fileMetadataService.uploadFile(any(), any(), any(), any(), any())).thenReturn(responseEntityMono);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("filename", filename);
        builder.part("type", type);
        builder.part("mimeType", mimeType);

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
}