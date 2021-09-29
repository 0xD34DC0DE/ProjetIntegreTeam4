package com.team4.backend.model;

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

    private String userId;
    private String assetId;
    private String filename;
    private boolean validCV;

    private LocalDateTime creationDate;

    public FileMetaData(String id, String userId, String assetId, String filename) {
        this.id = id;
        this.userId = userId;
        this.assetId = assetId;
        this.filename = filename;
        this.validCV = false;
        this.creationDate = LocalDateTime.now();
    }
}
