package com.team4.backend.testdata;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.model.FileMetaData;

public abstract class FileMetaDataMockData {

    public static FileMetaData getFileMetaData() {
        return FileMetaData.builder()
                .id("90ksj30sak2")
                .userEmail("123456@gmail.com")
                .filename("CV.pdf")
                .build();
    }

    public static FileMetaDataInternshipManagerViewDto getFileMetaDataDto() {
        return FileMetaDataInternshipManagerViewDto.builder()
                .id("90ksj30sak2")
                .userEmail("123456@gmail.com")
                .filename("CV.pdf")
                .build();
    }

}
