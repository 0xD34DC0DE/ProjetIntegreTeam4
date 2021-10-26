package com.team4.backend.mapping;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.dto.FileMetaDataStudentViewDto;
import com.team4.backend.model.FileMetaData;

public abstract class FileMetaDataMapper {

    public static FileMetaDataInternshipManagerViewDto toInternshipManagerViewDto(FileMetaData fileMetaData) {
        return FileMetaDataInternshipManagerViewDto.builder()
                .id(fileMetaData.getId())
                .assetId(fileMetaData.getAssetId())
                .userEmail(fileMetaData.getUserEmail())
                .filename(fileMetaData.getFilename())
                .uploadDate(fileMetaData.getUploadDate())
                .build();
    }

    public static FileMetaDataStudentViewDto toStudentViewDto(FileMetaData fileMetaData) {
        return FileMetaDataStudentViewDto.builder()
                .filename(fileMetaData.getFilename())
                .isValid(fileMetaData.getIsValid())
                .uploadDate(fileMetaData.getUploadDate())
                .seenDate(fileMetaData.getSeenDate())
                .build();
    }

}
