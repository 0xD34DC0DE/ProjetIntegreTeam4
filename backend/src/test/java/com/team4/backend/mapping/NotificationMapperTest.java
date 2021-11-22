package com.team4.backend.mapping;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;
import com.team4.backend.testdata.NotificationMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationMapperTest {

    @Test
    void mapNotificationDtoToEntity() {
        //ASSERT
        NotificationDto notificationDto = NotificationMockData.getNotificationDto();

        //ACT
        Notification notification = NotificationMapper.toEntity(notificationDto);

        //ARRANGE
        assertNull(notification.getId());
        assertEquals(notification.getReceiverIds(), notificationDto.getReceiverIds());
        assertEquals(notification.getData(), notificationDto.getData());
        assertEquals(notification.getContent(), notificationDto.getContent());
        assertEquals(notification.getSeenIds(), notificationDto.getSeenIds());
        assertEquals(notification.getNotificationType(), notificationDto.getNotificationType());
        assertEquals(notification.getTitle(), notificationDto.getTitle());
        assertNotNull(notification.getCreationDate());
    }

    @Test
    void mapNotificationEntityToDto() {
        //ASSERT
        Notification notification = NotificationMockData.getNotification();

        //ACT
        NotificationDto notificationDto = NotificationMapper.toDto(notification);

        //ARRANGE
        assertEquals(notification.getId(), notificationDto.getId());
        assertEquals(notification.getReceiverIds(), notificationDto.getReceiverIds());
        assertEquals(notification.getTitle(), notificationDto.getTitle());
        assertEquals(notification.getContent(), notificationDto.getContent());
        assertEquals(notification.getSeenIds(), notificationDto.getSeenIds());
        assertEquals(notification.getNotificationType(), notificationDto.getNotificationType());
        assertEquals(notification.getData(), notificationDto.getData());
        assertNotNull(notificationDto.getCreationDate());
    }

}
