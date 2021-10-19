package com.team4.backend.service;

import com.team4.backend.exception.UserNotFoundException;
import lombok.extern.java.Log;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Log
@Service
public class EmailSenderService {


    private final StudentService studentService;

    private final InternshipOfferService internshipOfferService;

    private final JavaMailSender javaMailSender;

    public EmailSenderService(StudentService studentService, InternshipOfferService internshipOfferService, JavaMailSender javaMailSender) {
        this.studentService = studentService;
        this.internshipOfferService = internshipOfferService;
        this.javaMailSender = javaMailSender;
    }

    public Mono<Void> sendEmailToStudent(String sender, String receiver, String subject, String content) {
        return studentService.findByEmail(receiver)
                .flatMap(student -> internshipOfferService.monitorOffersInterestedStudentsContainsStudentEmail(receiver, sender))
                .flatMap( studentInMonitorListResponse ->
                        studentInMonitorListResponse ?
                            sendEmail(sender, receiver, subject, content) :
                            Mono.error(new UserNotFoundException("Given receiver email does not correspond to student that applied to monitor's offer")));
    }

    protected Mono<Void> sendEmail(String sender, String receiver, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = getHelper(message);
            helper.setTo(new String[]{sender, receiver});
            helper.setSubject(subject);
            helper.setText(content);
        } catch (MessagingException e) {
            return Mono.error(e);
        }
        javaMailSender.send(message);
        return Mono.empty();
    }

    protected MimeMessageHelper getHelper(MimeMessage message) throws MessagingException {
        return new MimeMessageHelper(message, true);
    }
}
