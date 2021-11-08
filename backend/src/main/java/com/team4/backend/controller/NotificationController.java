package com.team4.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.backend.dto.NotificationDto;
import com.team4.backend.event.NotificationCreatedEvent;
import com.team4.backend.model.Notification;
import com.team4.backend.publisher.NotificationCreatedPublisher;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/notification")
@PreAuthorize("hasAnyAuthority('STUDENT', 'MONITOR')")
public class NotificationController {

    private final NotificationService notificationService;
    private final Flux<NotificationCreatedEvent> notificationCreatedEvents;
    private final ObjectMapper objectMapper;

    public NotificationController(NotificationService notificationService, ObjectMapper objectMapper, NotificationCreatedPublisher notificationCreatedPublisher) {
        this.notificationService = notificationService;
        this.notificationCreatedEvents = Flux.create(notificationCreatedPublisher).share();
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<Notification>> createNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.createNotification(notificationDto)
                .map(n -> ResponseEntity.status(HttpStatus.CREATED).body(n));
    }

    @GetMapping("/sse")
    public Flux<ServerSentEvent<String>> sseNotificationCreation(@RequestParam String channelId, Principal principal) {
        return notificationCreatedEvents
                .filter(event -> ((Notification) event.getSource()).getReceiverEmail().equals(UserSessionService.getLoggedUserEmail(principal)))
                .map(event -> {
                    try {
                        return ServerSentEvent.<String>builder()
                                .data(objectMapper.writeValueAsString(event.getSource()))
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
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
