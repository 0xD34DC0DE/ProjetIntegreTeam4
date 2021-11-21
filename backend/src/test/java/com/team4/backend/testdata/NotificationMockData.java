package com.team4.backend.testdata;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class NotificationMockData {

    public static Flux<Notification> getNotifications() {
        return Flux.just(
                Notification.notificationBuilder()
                        .id("615af41de2757ad51b0b02ed")
                        .receiverIds(Set.of("61930b84d3475148583fdff7"))
                        .title("Title 1")
                        .data(getNotificationData(2))
                        .content("this is content 1")
                        .build(),
                Notification.notificationBuilder()
                        .id("615af409e2757ad51b0b02df")
                        .receiverIds(Set.of("61930b84d3475148583fe010", "6192ec3bb711986676e590d9"))
                        .title("Title 2")
                        .content("this is content")
                        .data(Collections.emptyMap())
                        .build()
        );
    }


    public static Notification getNotification() {
        return Notification.notificationBuilder()
                .id("507f191e810c19729de860ea")
                .receiverIds(Set.of("61930b84d3475148583fdfee", "61930b84d3475148583fdff3"))
                .title("Title 1")
                .data(getNotificationData(3))
                .content("this is content 1")
                .build();
    }

    public static NotificationDto getNotificationDto() {
        return NotificationDto.notificationDtoBuilder()
                .id(null)
                .receiverIds(Set.of("61930b84d3475148583fdfed"))
                .title("Title 1")
                .data(getNotificationData(3))
                .creationDate(null)
                .content("this is content 1")
                .build();
    }

    public static Map<String, String> getNotificationData(int count) {
        Map<String, String> data = new HashMap<>();
        for (int i = 0; i <= count; i++) {
            data.put("id" + count, String.valueOf(i));
        }
        return data;
    }

}