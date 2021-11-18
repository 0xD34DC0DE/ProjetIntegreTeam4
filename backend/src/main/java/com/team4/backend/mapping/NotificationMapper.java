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
                .creationDate(null)
                .severity(notificationDto.getSeverity())
                .build();
    }

    public static NotificationDto toDto(Notification notification) {
        return NotificationDto.notificationDtoBuilder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .creationDate(notification.getCreationDate())
                .data(notification.getData())
                .severity(notification.getSeverity())
                .receiverIds(notification.getReceiverIds())
                .build();
    }

}
