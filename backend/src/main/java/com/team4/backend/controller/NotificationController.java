package com.team4.backend.controller;

import com.team4.backend.model.Notification;
import com.team4.backend.service.NotificationService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.security.Principal;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/stream")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public Flux<ServerSentEvent<Notification>> connect(Principal principal) {
        return null;
    }

}
