package com.team4.backend.mapping;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.model.FileMetaData;

public abstract class FileMetaDataMapper {

    public static FileMetaDataInternshipManagerViewDto toDto(FileMetaData fileMetaData){
        return FileMetaDataInternshipManagerViewDto.builder()
                .id(fileMetaData.getId())
                .userEmail(fileMetaData.getUserEmail())
                .filename(fileMetaData.getFilename())
                .uploadDate(fileMetaData.getCreationDate())
                .build();
    }
}
