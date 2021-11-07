package com.team4.backend.testdata;

import com.team4.backend.model.Notification;
import com.team4.backend.model.enums.NotificationSeverity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public abstract class NotificationMockData {

    public static Flux<Notification> getNotifications() {
        return Flux.just(
                Notification.notificationBuilder()
                        .id("615af41de2757ad51b0b02ed")
                        .receiverEmail("studentEmail@gmail.com")
                        .title("Title 1")
                        .creationDate(LocalDateTime.now())
                        .severity(NotificationSeverity.LOW)
                        .content("this is content 1")
                        .build(),
                Notification.notificationBuilder()
                        .id("615af409e2757ad51b0b02df")
                        .receiverEmail("monitorEmail@gmail.com")
                        .title("Title 2")
                        .severity(NotificationSeverity.HIGH)
                        .content("this is content")
                        .creationDate(LocalDateTime.now())
                        .build()
        );
    }

    public static Mono<Notification> getNotification() {
        return Mono.just(
                Notification.notificationBuilder()
                        .id("507f191e810c19729de860ea")
                        .receiverEmail("email@gmail.com")
                        .title("Title 1")
                        .severity(NotificationSeverity.LOW)
                        .creationDate(LocalDateTime.now())
                        .content("this is content 1")
                        .build()
        );
    }

}