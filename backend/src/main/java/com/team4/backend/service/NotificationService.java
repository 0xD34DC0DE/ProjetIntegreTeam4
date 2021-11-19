package com.team4.backend.service;

import com.mongodb.client.result.UpdateResult;
import com.team4.backend.dto.NotificationDto;
import com.team4.backend.mapping.NotificationMapper;
import com.team4.backend.model.Notification;
import com.team4.backend.repository.NotificationRepository;
import com.team4.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final Sinks.Many<Notification> sinks;

    public NotificationService(NotificationRepository notificationRepository, Sinks.Many<Notification> notificationFlux) {
        this.notificationRepository = notificationRepository;
        this.sinks = notificationFlux;
    }

    public Mono<Notification> createNotification(NotificationDto notificationDto) {
        return notificationRepository
                .save(NotificationMapper.toEntity(notificationDto))
                .doOnSuccess(sinks::tryEmitNext);
    }

    public Flux<Notification> findAllNotifications(String receiverId) {
        return notificationRepository
                .findAllByReceiverId(receiverId);
    }

    public Mono<Notification> deleteUserNotification(String notificationId, String userId) {
        return notificationRepository
                .deleteUserNotification(notificationId, userId);
    }

    public Sinks.Many<Notification> getNotificationFluxSink() {
        return sinks;
    }

}
