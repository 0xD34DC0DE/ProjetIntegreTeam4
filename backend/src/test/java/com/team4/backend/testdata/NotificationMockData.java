package com.team4.backend.testdata;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;
import com.team4.backend.model.enums.NotificationSeverity;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class NotificationMockData {

    public static Flux<Notification> getNotifications() {
        return Flux.just(
                Notification.notificationBuilder()
                        .id("615af41de2757ad51b0b02ed")
                        .receiverEmail("studentEmail@gmail.com")
                        .title("Title 1")
                        .creationDate(LocalDateTime.now())
                        .severity(NotificationSeverity.LOW)
                        .data(getNotificationData(2))
                        .content("this is content 1")
                        .build(),
                Notification.notificationBuilder()
                        .id("615af409e2757ad51b0b02df")
                        .receiverEmail("monitorEmail@gmail.com")
                        .title("Title 2")
                        .severity(NotificationSeverity.HIGH)
                        .content("this is content")
                        .data(Collections.emptyMap())
                        .creationDate(LocalDateTime.now())
                        .build()
        );
    }

    public static Notification getNotification() {
        return
                Notification.notificationBuilder()
                        .id("507f191e810c19729de860ea")
                        .receiverEmail("email@gmail.com")
                        .title("Title 1")
                        .severity(NotificationSeverity.LOW)
                        .creationDate(LocalDateTime.now())
                        .data(getNotificationData(3))
                        .content("this is content 1")
                        .build();
    }

    public static NotificationDto getNotificationDto() {
        return
                NotificationDto.notificationDtoBuilder()
                        .receiverEmail("email@gmail.com")
                        .title("Title 1")
                        .severity(NotificationSeverity.LOW)
                        .creationDate(LocalDateTime.now())
                        .data(getNotificationData(3))
                        .content("this is content 1")
                        .build();
    }

    public static Map<String, String> getNotificationData(int count) {
        Map<String, String> data = new HashMap<>();
        for(int i = 0; i <= count; i++) {
            data.put("id"+count, String.valueOf(i));
        }
        return data;
    }

}