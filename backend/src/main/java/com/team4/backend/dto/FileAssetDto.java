package com.team4.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
@NoArgsConstructor
public class FileAssetDto {

    @Id
    private String id;

    private Byte[] content;
}
