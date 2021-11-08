package com.team4.backend.service;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.model.Notification;
import com.team4.backend.repository.NotificationRepository;
import com.team4.backend.testdata.NotificationMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationService notificationService;

}
