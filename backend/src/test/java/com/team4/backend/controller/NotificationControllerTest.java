package com.team4.backend.controller;

import com.mongodb.client.result.UpdateResult;
import com.team4.backend.model.Notification;
import com.team4.backend.service.NotificationService;
import com.team4.backend.testdata.NotificationMockData;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = NotificationController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class NotificationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private NotificationService notificationService;

    @Test
    void shouldFindAllNotifications() {
        //ARRANGE
        String receiverId = "receiverId";
        Flux<Notification> notificationFlux = NotificationMockData.getNotifications();

        when(notificationService.findAllNotifications(receiverId)).thenReturn(notificationFlux);

        //ACT
        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/notification")
                                .queryParam("receiverId", receiverId)
                                .build()
                )
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(Notification.class);
    }

    @Test
    void shouldDeleteNotificationForUser() {
        //ARRANGE
        String notificationId = "notificationId";
        String userId = "userId";

        Notification notification = NotificationMockData.getNotification();

        when(notificationService.deleteUserNotification(notificationId, userId)).thenReturn(Mono.just(notification));

        //ACT
        webTestClient
                .delete()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/notification")
                                .queryParam("notificationId", notificationId)
                                .queryParam("userId", userId)
                                .build()
                )
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(String.class);
    }

}
