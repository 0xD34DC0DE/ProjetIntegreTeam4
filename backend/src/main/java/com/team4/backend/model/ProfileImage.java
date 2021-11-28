package com.team4.backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@Builder
@Document(collection = "profileImages")
public class ProfileImage implements Serializable {

    @Id
    private String id;
    private Binary image;
    private String fileName;
    private String uploaderId;
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @Builder(builderMethodName = "profileImageBuilder")
    public ProfileImage(String id,
                        Binary image,
                        String fileName,
                        String uploaderId,
                        LocalDateTime creationDate) {
        this.id = id;
        this.image = image;
        this.fileName = fileName;
        this.uploaderId = uploaderId;
        this.creationDate = Optional.ofNullable(creationDate).orElse(LocalDateTime.now());
    }

}
