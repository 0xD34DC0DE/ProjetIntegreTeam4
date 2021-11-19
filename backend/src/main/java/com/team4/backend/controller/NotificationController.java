package com.team4.backend.controller;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;
import com.team4.backend.service.NotificationService;
import com.team4.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/notification")
@PreAuthorize("hasAnyAuthority('STUDENT', 'MONITOR', 'INTERNSHIP_MANAGER', 'SUPERVISOR')")
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
    public Flux<ServerSentEvent<Notification>> sseNotificationStream(@RequestParam String userId) {
        return notificationService
                .getNotificationFluxSink()
                .asFlux()
                .delayElements(Duration.ofSeconds(5))
                .filter(n -> n.getReceiverIds().contains(userId))
                .map(n -> ServerSentEvent.<Notification>builder()
                        .data(n)
                        .build());
    }

    @GetMapping
    public Flux<Notification> findAllNotifications(@RequestParam String receiverId) {
        return notificationService
                .findAllNotifications(receiverId);
    }

    @DeleteMapping
    public Mono<ResponseEntity<String>> deleteNotificationForUser(@RequestParam String notificationId, @RequestParam String userId) {
        return notificationService
                .deleteUserNotification(notificationId, userId)
                .thenReturn(ResponseEntity.ok(""));
    }

}
