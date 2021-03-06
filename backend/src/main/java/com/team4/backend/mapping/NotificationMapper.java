package com.team4.backend.mapping;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;

import java.time.LocalDateTime;

public abstract class NotificationMapper {

    public static Notification toEntity(NotificationDto notificationDto) {
        return Notification.notificationBuilder()
                .id(null)
                .title(notificationDto.getTitle())
                .receiverIds(notificationDto.getReceiverIds())
                .content(notificationDto.getContent())
                .data(notificationDto.getData())
                .seenIds(notificationDto.getSeenIds())
                .creationDate(null)
                .notificationType(notificationDto.getNotificationType())
                .build();
    }

    public static NotificationDto toDto(Notification notification) {
        return NotificationDto.notificationDtoBuilder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .seenIds(notification.getSeenIds())
                .creationDate(notification.getCreationDate())
                .data(notification.getData())
                .receiverIds(notification.getReceiverIds())
                .notificationType(notification.getNotificationType())
                .build();
    }

}
