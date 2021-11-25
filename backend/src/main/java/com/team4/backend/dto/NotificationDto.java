package com.team4.backend.dto;

import com.team4.backend.model.enums.NotificationType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
public class NotificationDto implements Serializable {

    private String id;
    private String title;
    private String content;
    private Set<String> receiverIds;
    private Map<String, String> data;
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();
    private Set<String> seenIds;
    private NotificationType notificationType;

    @Builder(builderMethodName = "notificationDtoBuilder")
    public NotificationDto(String id,
                           String title,
                           String content,
                           Set<String> receiverIds,
                           Map<String, String> data,
                           LocalDateTime creationDate,
                           Set<String> seenIds,
                           NotificationType notificationType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.data = data;
        this.receiverIds = receiverIds;
        this.creationDate = Optional.ofNullable(creationDate).orElse(LocalDateTime.now());
        this.seenIds = seenIds;
        this.notificationType = notificationType;
    }

}
