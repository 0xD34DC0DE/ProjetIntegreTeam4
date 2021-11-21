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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

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
        String receiverId = "receiverId";
        Flux<Notification> notificationFluxMockData = NotificationMockData.getNotifications();

        //when(notificationService.findAllNotifications(receiverId)).thenReturn(notificationFluxMockData);

        //ACT
        //Flux<Notification> notificationFlux = notificationRepository.findAllByReceiverId(receiverId);

        //ASSERT
//        StepVerifier
//                .create(notificationFlux)
//                .assertNext(Assertions::assertNotNull)
//                .expectNextCount(1)
//                .verifyComplete();
    }

    @Test
    void shouldDeleteUserNotification() {
        //ARRANGE
        String notificationId = "notificationId";
        String userId = "userId";

        Notification notification = NotificationMockData.getNotification();

        when(notificationRepository.deleteUserNotification(notificationId, userId)).thenReturn(Mono.just(notification));

        //ACT
        Mono<Notification> notificationMono = notificationRepository.deleteUserNotification(notificationId, userId);

        //ASSERT
        StepVerifier
                .create(notificationMono)
                .assertNext(Assertions::assertNotNull)
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

}
