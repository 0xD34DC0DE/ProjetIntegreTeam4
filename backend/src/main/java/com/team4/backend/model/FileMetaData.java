package com.team4.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.flapdoodle.embed.process.config.store.FileType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
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

    private String userEmail;//TODO --> change for email

    private String filename;

    private FileType type;

    private Boolean isValid;

    private Boolean isSeen;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    private LocalDateTime seenDate;

    @Builder
    public FileMetaData(String id, String userEmail, String filename) {
        this.id = id;
        this.userEmail = userEmail;
        this.filename = filename;
        this.isValid = false;
        this.isSeen = false;
        this.creationDate = LocalDateTime.now();
    }
}
