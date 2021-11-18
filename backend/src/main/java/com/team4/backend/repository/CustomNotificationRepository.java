package com.team4.backend.repository;

import com.team4.backend.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomNotificationRepository {
    Flux<Notification> findAllByReceiverId(String id);

    Mono<Notification> deleteUserNotification(String notificationId, String userId);
}
