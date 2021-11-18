package com.team4.backend.repository;

import com.mongodb.client.result.UpdateResult;
import com.team4.backend.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomNotificationRepository {
    Flux<Notification> findByReceiverId(String id);
    Mono<UpdateResult> deleteUserNotification(String notificationId, String userId);
}
