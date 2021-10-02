package com.team4.backend.model;

import com.team4.backend.model.enums.FileType;
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
public class FileMetadata {

    @Id
    private String id;

    private String userEmail;

    private String assetId;

    private String filename;

    private boolean validCV;

    private FileType type;

    private LocalDateTime creationDate;

    @Builder
    public FileMetadata(String id, String userEmail, String assetId, String filename, boolean validCV, FileType type, LocalDateTime creationDate) {
        this.id = id;
        this.userEmail = userEmail;
        this.assetId = assetId;
        this.filename = filename;
        this.validCV = validCV;
        this.type = type;
        this.creationDate = creationDate;
    }
}
