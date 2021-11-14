package com.team4.backend.mapping;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;

import java.time.LocalDateTime;

public abstract class NotificationMapper {

    public static Notification toEntity(NotificationDto notificationDto) {
        return Notification.notificationBuilder()
                .title(notificationDto.getTitle())
                .receiverId(notificationDto.getReceiverId())
                .content(notificationDto.getContent())
                .data(notificationDto.getData())
                .severity(notificationDto.getSeverity())
                .creationDate(LocalDateTime.now())
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
                .receiverId(notification.getReceiverId())
                .build();
    }

}