package com.team4.backend.repository;

import com.team4.backend.model.Notification;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomNotificationRepository {

    Flux<Notification> findAllByReceiverId(String id, Pageable pageable);

    Mono<Notification> deleteUserNotification(String notificationId, String userId);

    Mono<Notification> addUserToSeenNotification(String userId, String notificationId);

}
