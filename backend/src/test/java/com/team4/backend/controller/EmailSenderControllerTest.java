package com.team4.backend.controller;

import com.team4.backend.service.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = EmailSenderController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
class EmailSenderControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    EmailSenderService emailSenderService;

    final String sender = "sender";
    final String receiver = "receiver";
    final String subject = "subject";
    final String content = "content";

    final String principalEmail = "principal@gmail.com";

    @Test
    void shouldSendEmailToStudent() {
        //ARRANGE
        when(emailSenderService.sendEmailToStudent(principalEmail, receiver, subject, content)).thenReturn(Mono.empty());

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("sender", principalEmail);
        builder.part("receiver", receiver);
        builder.part("subject", subject);
        builder.part("content", content);

        MultiValueMap<String, HttpEntity<?>> multiValueMap = builder.build();

        //ACT
        webTestClient
                .post()
                .uri("/emailsender")
                .bodyValue(multiValueMap)
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(String.class);
    }
}