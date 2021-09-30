package com.team4.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@Document(collection = "fileMetadata")
public class FileMetaData {
    @Id
    private String id;

    private String assetId;

    private String userId;

    private String filename;

    private Boolean isValid;

    private Boolean isSeen;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @Builder
    public FileMetaData(String id, String userId, String filename) {
        this.id = id;
        this.userId = userId;
        this.filename = filename;
        this.isValid = false;
        this.isSeen = false;
        this.creationDate = LocalDateTime.now();
    }
}
