package com.team4.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@NoArgsConstructor
public class FileAsset {

    @Id
    private String id;

    private Byte[] content;
}
