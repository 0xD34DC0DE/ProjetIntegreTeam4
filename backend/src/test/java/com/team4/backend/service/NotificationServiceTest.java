package com.team4.backend.service;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.mapping.NotificationMapper;
import com.team4.backend.model.Notification;
import com.team4.backend.repository.NotificationRepository;
import com.team4.backend.testdata.NotificationMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    Sinks.Many<Notification> sinks;

    @InjectMocks
    NotificationService notificationService;

    @Test
    void shouldFindAllNotifications() {
        //ARRANGE
        String receiverId = "61930b84d3475148583fdff7";
        Flux<Notification> notificationFluxMockData = NotificationMockData.getNotifications();
        PageRequest pageRequest = PageRequest.of(1, 5);

        when(notificationRepository.findAllByReceiverId(receiverId, pageRequest)).thenReturn(notificationFluxMockData);

        //ACT
        Flux<Notification> notificationFlux = notificationRepository.findAllByReceiverId(receiverId, pageRequest);

        //ASSERT
        StepVerifier
                .create(notificationFlux)
                .assertNext(Assertions::assertNotNull)
                .assertNext(notification -> assertTrue(notification.getReceiverIds().contains(receiverId)))
                .verifyComplete();
    }

    @Test
    void shouldDeleteUserNotification() {
        //ARRANGE
        String notificationId = "507f191e810c19729de860ea";
        String userId = "61930b84d3475148583fdfee";

        Notification notification = NotificationMockData.getNotification();

        when(notificationRepository.deleteUserNotification(notificationId, userId)).thenReturn(Mono.just(notification));

        //ACT
        Mono<Notification> notificationMono = notificationRepository.deleteUserNotification(notificationId, userId);

        //ASSERT
        StepVerifier
                .create(notificationMono)
                .assertNext(n -> assertTrue(n.getReceiverIds().contains(userId)))
                .verifyComplete();
    }

    @Test
    void shouldCreateNotification() {
        //ARRANGE
        NotificationDto notificationDto = NotificationMockData.getNotificationDto();
        Notification notification = NotificationMockData.getNotification();

        when(notificationRepository.save(NotificationMapper.toEntity(notificationDto))).thenReturn(Mono.just(notification));

        //ACT
        Mono<Notification> notificationMono = notificationService.createNotification(notificationDto);

        //ASSERT
        StepVerifier
                .create(notificationMono)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    void shouldAddUserToSeenNotification() {
        //ARRANGE
        Notification notification = NotificationMockData.getNotification();
        String userId = "userId";

        Notification updatedNotification = NotificationMockData.getNotification();
        Set<String> updatedSeenIds = new HashSet<>(notification.getSeenIds());
        updatedSeenIds.add(userId);
        updatedNotification.setSeenIds(updatedSeenIds);

        when(notificationRepository.addUserToSeenNotification(userId, notification.getId())).thenReturn(Mono.just(updatedNotification));

        //ACT
        Mono<Notification> notificationMono = notificationRepository.addUserToSeenNotification(userId, notification.getId());

        //ASSERT
        StepVerifier
                .create(notificationMono)
                .assertNext(n -> assertTrue(n.getSeenIds().contains(userId)))
                .verifyComplete();
    }

}
