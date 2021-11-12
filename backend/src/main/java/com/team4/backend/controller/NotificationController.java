package com.team4.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/notification")
@PreAuthorize("hasAnyAuthority('STUDENT', 'MONITOR')")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<Notification>> createNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.createNotification(notificationDto)
                .map(n -> ResponseEntity.status(HttpStatus.CREATED).body(n));
    }

    @GetMapping("/sse")
    public Flux<ServerSentEvent<Notification>> sseNotificationStream(Principal principal) {
        return notificationService.getNotificationFluxSink().asFlux()
                .filter(n -> n.getReceiverEmail().equals(UserSessionService.getLoggedUserEmail(principal)))
                .map(n -> ServerSentEvent.<Notification>builder()
                        .data(n)
                        .build());
    }

    @GetMapping
    public Flux<Notification> findAllNotifications(Principal principal) {
        return notificationService
                .findAllNotifications(principal);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteNotification(@PathVariable String id) {
        return notificationService
                .deleteNotification(id)
                .thenReturn(ResponseEntity.ok(""));
    }

}
