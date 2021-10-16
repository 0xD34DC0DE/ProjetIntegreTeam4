package com.team4.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.publisher.Mono.when;


@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailSenderService emailSenderService;

    final String receiver = "receiver";
    final String subject = "subject";
    final String content = "content";

    final String principalEmail = "principal@gmail.com";



    @Test
    void sendEmailToStudent() {
        //ARRANGE
        when(emailSenderService.)

        //ACT
        Mono<Void> response = emailSenderService.sendEmailToStudent(principalEmail, receiver, subject, content);

        //ASSERT
        StepVerifier.create(response).verifyComplete();

    }
}