package com.team4.backend.testdata;

import com.team4.backend.model.Notification;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public abstract class NotificationMockData {

    public static Flux<Notification> getNotifications() {
        return Flux.just(
                Notification.notificationBuilder()
                        .id("13441bc539894b441c1e17d0")
                        .receiverEmail("studentEmail@gmail.com")
                        .title("Title 1")
                        .creationDate(LocalDateTime.now())
                        .content("this is content 1")
                        .build(),
                Notification.notificationBuilder()
                        .id("21561be512393a471a1e12kd")
                        .receiverEmail("monitorEmail@gmail.com")
                        .title("Title 2")
                        .content("this is content")
                        .creationDate(LocalDateTime.now())
                        .build()
        );
    }

}