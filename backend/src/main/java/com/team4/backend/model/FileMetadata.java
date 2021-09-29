package com.team4.backend.model;

import com.team4.backend.model.enums.FileType;
import lombok.Data;
import lombok.Generated;
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

    private String userId;
    private String assetId;
    private String filename;
    private boolean validCV;

    private FileType type;

    private LocalDateTime creationDate;
}
