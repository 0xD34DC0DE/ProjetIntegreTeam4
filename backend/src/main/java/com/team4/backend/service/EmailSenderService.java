package com.team4.backend.service;

import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.StudentRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Log
@Service
public class EmailSenderService {

    @Autowired
    MonitorRepository monitorRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InternshipOfferService internshipOfferService;

    @Autowired(required = false)
    JavaMailSender javaMailSender;

    public Mono<Void> sendEmailToStudent(String sender, String receiver, String subject, String content, String principalEmail) {

        return monitorExistsByEmailAndIsEnabledTrue(sender)
                .flatMap(s -> {
                    if (s) {
                        return monitorExistsByEmailAndIsEnabledTrue(receiver);
                    } else {
                        return Mono.error(new UserNotFoundException("Given sender email does not correspond to any saved monitor"));
                    }
                })
//                .flatMap(t -> {
//                    if (t) {
//                        return studentIsInMonitorList(receiver, sender);
//                    } else {
//                        return Mono.error(new UserNotFoundException("Given receiver email does not correspond to any saved student"));
//                    }
//                })
                .flatMap( u -> {
                    if (!u) {
                        // temporary sout to commit before the weekend
                        return Mono.error(new UserNotFoundException("Given receiver email does not correspond to any saved student"));
                        // return Mono.error(new UserNotFoundException("Given receiver email does not correspond to student that applied to monitor's offer"));
                    }
                    MimeMessage message = javaMailSender.createMimeMessage();
                    try {
                        MimeMessageHelper helper = new MimeMessageHelper(message, true);
                        helper.setTo(sender);
                        helper.setTo(receiver);
                        helper.setSubject(subject);
                        helper.setText(content);
                    } catch (MessagingException e) {
                        return Mono.error(e);
                    }
                    javaMailSender.send(message);
                    return Mono.empty();
                });
    }

    public Mono<Boolean> monitorExistsByEmailAndIsEnabledTrue(String email) {
        return monitorRepository.existsByEmailAndIsEnabledTrue(email);
    }

    public Mono<Boolean> studentExistsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    public Mono<Boolean> studentIsInMonitorList(String studentEmail, String monitorEmail) {
        System.out.println("dans le service studentIsInMonitorList");
        return internshipOfferService.isStudentOnMonitorOffer(studentEmail, monitorEmail).flatMap(s -> {
            if (s) {
                return Mono.just(true);
            } else {
                return Mono.just(false);
            }
        });
    }
}
