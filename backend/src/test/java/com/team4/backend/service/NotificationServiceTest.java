package com.team4.backend.service;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;
import com.team4.backend.repository.NotificationRepository;
import com.team4.backend.testdata.NotificationMockData;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationService notificationService;

    @Test
    void shouldFindAllNotifications() {
        //ARRANGE
        String receiverId = "receiverId";
        Flux<Notification> notificationFluxMockData = NotificationMockData.getNotifications();

        when(notificationService.findAllNotifications(receiverId)).thenReturn(notificationFluxMockData);

        //ACT
        Flux<Notification> notificationFlux =  notificationRepository.findAllByReceiverId(receiverId);

        //ASSERT
        StepVerifier
                .create(notificationFlux)
                .assertNext(Assertions::assertNotNull)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldDeleteUserNotification() {
        //ARRANGE
        String notificationId = "notificationId";
        String userId = "userId";

        Notification notification = NotificationMockData.getNotification();

        when(notificationService.deleteUserNotification(notificationId, userId)).thenReturn(Mono.just(notification));

        //ACT
        Mono<Notification> notificationMono = notificationService.deleteUserNotification(notificationId, userId);

        //ASSERT
        StepVerifier
                .create(notificationMono)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    // TODO: FIX IT
    @Test
    void shouldCreateNotification() {
        //ARRANGE
        NotificationDto notificationDto = NotificationMockData.getNotificationDto();
        Notification notification = NotificationMockData.getNotification();

        when(notificationService.createNotification(notificationDto)).thenReturn(Mono.just(notification));

        //ACT
        Mono<Notification> notificationMono = notificationService.createNotification(notificationDto);

        //ASSERT
        StepVerifier
                .create(notificationMono)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

}
