package com.team4.backend.service;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.mapping.NotificationMapper;
import com.team4.backend.model.Notification;
import com.team4.backend.repository.NotificationRepository;
import com.team4.backend.security.UserSessionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.security.Principal;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Sinks.Many<Notification> sinks;

    public NotificationService(NotificationRepository notificationRepository, Sinks.Many<Notification> notificationFlux) {
        this.notificationRepository = notificationRepository;
        this.sinks = notificationFlux;
    }

    public Mono<Notification> createNotification(NotificationDto notification) {
        return notificationRepository
                .save(NotificationMapper.toEntity(notification))
                .doOnSuccess(sinks::tryEmitNext);
    }

    public Flux<Notification> findAllNotifications(Principal principal) {
        return notificationRepository
                .findByReceiverEmail(UserSessionService.getLoggedUserEmail(principal));
    }

    public Mono<Void> deleteNotification(String id) {
        return notificationRepository
                .deleteById(id);
    }

    public Sinks.Many<Notification> getNotificationFluxSink(){
        return sinks;
    }

}
