package com.team4.backend.mapping;

import com.team4.backend.dto.FileMetaDataDto;
import com.team4.backend.model.FileMetaData;

public abstract class FileMetaDataMapper {

    public static FileMetaDataDto toDto(FileMetaData fileMetaData){
        return FileMetaDataDto.builder()
                .id(fileMetaData.getId())
                .userEmail(fileMetaData.getUserEmail())
                .filename(fileMetaData.getFilename())
                .isValid(fileMetaData.getIsValid())
                .creationDate(fileMetaData.getCreationDate())
                .seenDate(fileMetaData.getSeenDate())
                .build();
    }
}
