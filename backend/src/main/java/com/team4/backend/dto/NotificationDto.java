package com.team4.backend.dto;

import com.team4.backend.model.enums.NotificationSeverity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
public class NotificationDto implements Serializable {

    private String id;
    private String title;
    private String content;
    private String receiverEmail;
    private NotificationSeverity severity;
    private LocalDateTime creationDate;

    @Builder(builderMethodName = "notificationDtoBuilder")
    public NotificationDto(String id,
                           String title,
                           String content,
                           NotificationSeverity severity,
                           String receiverEmail,
                           LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.severity = severity;
        this.receiverEmail = receiverEmail;
        this.creationDate = Optional.ofNullable(creationDate).orElse(LocalDateTime.now());
    }

}
