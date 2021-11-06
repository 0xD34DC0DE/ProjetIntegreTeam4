package com.team4.backend.mapping;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;

import java.time.LocalDateTime;

public abstract class NotificationMapper {

    public static Notification toEntity(NotificationDto notificationDto) {
        return Notification.notificationBuilder()
                .title(notificationDto.getTitle())
                .receiverEmail(notificationDto.getReceiverEmail())
                .content(notificationDto.getContent())
                .creationDate(LocalDateTime.now())
                .build();
    }

    public static NotificationDto toDto(Notification notification) {
        return NotificationDto.notificationDtoBuilder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .creationDate(notification.getCreationDate())
                .receiverEmail(notification.getReceiverEmail())
                .build();
    }

}
