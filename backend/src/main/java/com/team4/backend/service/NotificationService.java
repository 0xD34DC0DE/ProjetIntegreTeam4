package com.team4.backend.service;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.event.NotificationCreatedEvent;
import com.team4.backend.mapping.NotificationMapper;
import com.team4.backend.model.Notification;
import com.team4.backend.repository.NotificationRepository;
import com.team4.backend.security.UserSessionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public NotificationService(NotificationRepository notificationRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.notificationRepository = notificationRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Mono<Notification> createNotification(NotificationDto notification) {
        return notificationRepository
                .save(NotificationMapper.toEntity(notification))
                .doOnSuccess(n -> applicationEventPublisher.publishEvent(new NotificationCreatedEvent(n)));
    }

    public Flux<Notification> findAllNotifications(Principal principal) {
        return notificationRepository
                .findByReceiverEmail(UserSessionService.getLoggedUserEmail(principal));
    }

    public Mono<Void> deleteNotification(String id) {
        return notificationRepository
                .deleteById(id);
    }

}
