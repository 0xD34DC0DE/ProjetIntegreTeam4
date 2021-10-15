package com.team4.backend.service;

import com.team4.backend.model.Student;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {

    @Mock
    MonitorRepository monitorRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailSenderService emailSenderService;

    final String sender = "sender";
    final String receiver = "receiver";
    final String subject = "subject";
    final String content = "content";

    final String principalEmail = "principal@gmail.com";



    @Test
    void sendEmailToStudent() {
//        //ARRANGE
        when(monitorRepository.existsByEmailAndIsEnabledTrue(sender)).thenReturn(Mono.just(true));
        when(studentRepository.existsByEmail(receiver)).thenReturn(Mono.just(true));

        Mono<Void> response = emailSenderService.sendEmailToStudent(sender, receiver, subject, content, principalEmail);

        //ACT & ASSERT
        StepVerifier.create(response).verifyComplete();

    }
}