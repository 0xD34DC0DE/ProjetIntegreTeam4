package com.team4.backend.controller;

import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/emailsender")
public class EmailSenderController {

    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MONITOR')")
    public Mono<ResponseEntity<String>> sendEmailToStudent(
            @RequestPart("receiver") String receiver,
            @RequestPart("subject") String subject,
            @RequestPart("content") String content,
            Principal principal) {
        return emailSenderService.sendEmailToStudent(UserSessionService.getLoggedUserEmail(principal), receiver, subject, content)
                .flatMap(u -> Mono.just(ResponseEntity.status(HttpStatus.OK).body("")));
    }

}
