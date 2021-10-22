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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.team4.backend.testdata.EmailSenderMockData;

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


    @InjectMocks
    EmailSenderService emailSenderService;


    @Test
    void shouldSendEmailToStudent() {
        //ARRANGE
        EmailSenderService emailSenderServiceSpy = spy(emailSenderService);
        doReturn(Mono.empty()).when(emailSenderServiceSpy).sendEmail(any(), any(), any(), any());

        when(studentService.findByEmail(EmailSenderMockData.getReceiver())).thenReturn(Mono.just(new Student()));
        when(internshipOfferService.isStudentEmailInMonitorOffersInterestedStudents(EmailSenderMockData.getReceiver(), EmailSenderMockData.getPrincipalEmail())).thenReturn(Mono.just(true));

        //ACT
        Mono<Void> response = emailSenderServiceSpy.sendEmailToStudent(EmailSenderMockData.getPrincipalEmail(), EmailSenderMockData.getReceiver(), EmailSenderMockData.getSubject(), EmailSenderMockData.getContent());

        //ASSERT
        StepVerifier.create(response).verifyComplete();
    }

    @Test
    void shouldNotSendEmailToStudentInvalidReceiver() {
        //ARRANGE
        when(studentService.findByEmail(EmailSenderMockData.getReceiver())).thenReturn(Mono.error(new UserNotFoundException()));

        //ACT
        Mono<Void> response = emailSenderService.sendEmailToStudent(EmailSenderMockData.getPrincipalEmail(), EmailSenderMockData.getReceiver(), EmailSenderMockData.getSubject(), EmailSenderMockData.getContent());

        //ASSERT
        StepVerifier.create(response).expectErrorMatches(throwable -> throwable instanceof UserNotFoundException).verify();
    }

    @Test
    void shouldNotSendEmailToStudentNotInList() {
        //ARRANGE
        when(studentService.findByEmail(EmailSenderMockData.getReceiver())).thenReturn(Mono.just(new Student()));
        when(internshipOfferService.isStudentEmailInMonitorOffersInterestedStudents(EmailSenderMockData.getReceiver(), EmailSenderMockData.getPrincipalEmail())).thenReturn(Mono.just(false));

        //ACT
        Mono<Void> response = emailSenderService.sendEmailToStudent(EmailSenderMockData.getPrincipalEmail(), EmailSenderMockData.getReceiver(), EmailSenderMockData.getSubject(), EmailSenderMockData.getContent());

        //ASSERT
        StepVerifier.create(response).expectErrorMatches(throwable -> throwable instanceof UserNotFoundException).verify();
    }

    @Test
    void shouldNotSendEmailToStudentNullPointerException() {
        // ACT & ASSERT
        assertThrows(NullPointerException.class, () -> emailSenderService.sendEmailToStudent(EmailSenderMockData.getPrincipalEmail(), EmailSenderMockData.getReceiver(), EmailSenderMockData.getSubject(), EmailSenderMockData.getContent()));
    }

    @Test
    void shouldSendEmail() {
        // ARRANGE
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));

        // ACT
        Mono<Void> response = emailSenderService.sendEmail(EmailSenderMockData.getPrincipalEmail(), EmailSenderMockData.getReceiver(), EmailSenderMockData.getSubject(), EmailSenderMockData.getContent());

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
        Mono<Void> response = emailSenderServiceSpy.sendEmail(EmailSenderMockData.getPrincipalEmail(), EmailSenderMockData.getReceiver(), EmailSenderMockData.getSubject(), EmailSenderMockData.getContent());

        //ASSERT
        StepVerifier.create(response).expectErrorMatches(throwable -> throwable instanceof MessagingException).verify();
    }
}