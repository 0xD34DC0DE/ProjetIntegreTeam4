package com.team4.backend.service;

import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    StudentService studentService;

    @Mock
    InternshipOfferService internshipOfferService;

    @Mock
    MimeMessageHelper mimeMessageHelper;

    @InjectMocks
    EmailSenderService emailSenderService;

    final String receiver = "receiver";
    final String subject = "subject";
    final String content = "content";

    final String principalEmail = "principal@gmail.com";

    @Test
    void shouldSendEmailToStudent() {
        //ARRANGE
        EmailSenderService emailSenderServiceSpy = spy(emailSenderService);
        doReturn(Mono.empty()).when(emailSenderServiceSpy).sendEmail(any(), any(), any(), any());

        doReturn(Mono.just(new Student())).when(studentService).findByEmail(receiver);
        doReturn(Mono.just(true)).when(internshipOfferService).monitorOffersInterestedStudentsContainsStudentEmail(receiver, principalEmail);

        //ACT
        Mono<Void> response = emailSenderServiceSpy.sendEmailToStudent(principalEmail, receiver, subject, content);

        //ASSERT
        StepVerifier.create(response).verifyComplete();
    }

    @Test
    void shouldNotSendEmailToStudentInvalidReceiver() {
        //ARRANGE
        doReturn(Mono.error(new UserNotFoundException())).when(studentService).findByEmail(receiver);

        //ACT
        Mono<Void> response = emailSenderService.sendEmailToStudent(principalEmail, receiver, subject, content);

        //ASSERT
        StepVerifier.create(response).expectErrorMatches(throwable -> throwable instanceof UserNotFoundException).verify();
    }

    @Test
    void shouldNotSendEmailToStudentNotInList() {
        //ARRANGE
        doReturn(Mono.just(new Student())).when(studentService).findByEmail(receiver);
        doReturn(Mono.just(false)).when(internshipOfferService).monitorOffersInterestedStudentsContainsStudentEmail(receiver, principalEmail);

        //ACT
        Mono<Void> response = emailSenderService.sendEmailToStudent(principalEmail, receiver, subject, content);

        //ASSERT
        StepVerifier.create(response).expectErrorMatches(throwable -> throwable instanceof UserNotFoundException).verify();
    }

    @Test
    void shouldNotSendEmailToStudentNullPointerException() {
        // ACT & ASSERT
        assertThrows(NullPointerException.class, () -> emailSenderService.sendEmailToStudent(principalEmail, receiver, subject, content));
    }

    @Test
    void shouldSendEmail() {
        // ARRANGE
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));

        // ACT
        Mono<Void> response = emailSenderService.sendEmail(principalEmail, receiver, subject, content);

        // ASSERT
        StepVerifier.create(response).verifyComplete();
    }

    @Test
    void shouldNotSendEmail() throws MessagingException {
        // ARRANGE
        EmailSenderService emailSenderServiceSpy = spy(emailSenderService);
        doThrow(new MessagingException()).when(emailSenderServiceSpy).getHelper(any());

        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));

        // ACT
        Mono<Void> response = emailSenderServiceSpy.sendEmail(principalEmail, receiver, subject, content);

        //ASSERT
        StepVerifier.create(response).expectErrorMatches(throwable -> throwable instanceof MessagingException).verify();
    }
}