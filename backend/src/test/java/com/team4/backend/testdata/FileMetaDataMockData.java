package com.team4.backend.testdata;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.model.enums.UploadType;

import java.time.LocalDateTime;

public abstract class FileMetaDataMockData {

    public static FileMetaData getFileMetaData() {
        return FileMetaData.builder()
                .id("90ksj30sak2")
                .assetId("90osd0=34329dsk342")
                .userEmail("123456@gmail.com")
                .filename("CV.pdf")
                .type(UploadType.CV)
                .uploadDate(LocalDateTime.now())
                .seenDate(LocalDateTime.now().plusWeeks(1))
                .isValid(false)
                .build();
    }

    public static FileMetaDataInternshipManagerViewDto getFileMetaDataInternshipManagerViewDto() {
        return FileMetaDataInternshipManagerViewDto.builder()
                .id("90ksj30sak2")
                .assetId("90osd0=34329dsk342")
                .userEmail("123456@gmail.com")
                .filename("CV.pdf")
                .build();
    }

}
