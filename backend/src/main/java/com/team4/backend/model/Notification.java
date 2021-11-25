package com.team4.backend.model;

import com.team4.backend.model.enums.NotificationType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Document(collection = "notifications")
public class Notification implements Serializable {

    @Id
    private String id;
    private String title;
    private String content;
    @Builder.Default
    private Set<String> receiverIds = Set.of();
    private Map<String, String> data;
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();
    @Builder.Default
    private Set<String> seenIds = Set.of();
    private NotificationType notificationType;

    @Builder(builderMethodName = "notificationBuilder")
    public Notification(String id,
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
        this.receiverIds = receiverIds;
        this.data = data;
        this.creationDate = Optional.ofNullable(creationDate).orElse(LocalDateTime.now());
        this.seenIds = Set.of();
        this.notificationType = notificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(receiverIds, that.receiverIds) && Objects.equals(seenIds, that.seenIds) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, receiverIds, seenIds, data);
    }

}
