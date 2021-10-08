package com.team4.backend.model;

import de.flapdoodle.embed.process.config.store.FileType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@Document(collection = "fileMetadata")
public class FileMetaData implements Serializable {

    private String id;

    private String assetId;

    private String userEmail;

    private String filename;

    private String path;

    private FileType type;

    private Boolean isValid;

    private Boolean isSeen;

    private LocalDateTime uploadDate;

    private LocalDateTime seenDate;

    @Builder
    public FileMetaData(String id, String assetId, String userEmail, String filename, String path, FileType type, Boolean isValid, Boolean isSeen, LocalDateTime uploadDate, LocalDateTime seenDate) {
        this.id = id;
        this.assetId = assetId;
        this.userEmail = userEmail;
        this.filename = filename;
        this.path = path;
        this.type = type;
        this.isValid = isValid;
        this.isSeen = isSeen;
        this.uploadDate = uploadDate;
        this.seenDate = seenDate;
    }
}
