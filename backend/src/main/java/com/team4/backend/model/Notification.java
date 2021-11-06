package com.team4.backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@Document(collection = "notifications")
public class Notification implements Serializable {

    @Id
    private String id;
    private String title;
    private String content;
    private String receiverEmail;
    private LocalDateTime creationDate;

    @Builder(builderMethodName = "notificationBuilder")
    public Notification(String id,
                        String title,
                        String content,
                        String receiverEmail,
                        LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.receiverEmail = receiverEmail;
        this.creationDate = Optional.ofNullable(creationDate).orElse(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "aaa";
    }

}
