package com.team4.backend.testdata;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.dto.FileMetaDataStudentViewDto;
import com.team4.backend.model.FileMetaData;

import java.time.LocalDateTime;

public abstract class FileMetaDataMockData {

    public static FileMetaData getFileMetaData() {
        return FileMetaData.builder()
                .id("90ksj30sak2")
                .userEmail("123456@gmail.com")
                .filename("CV.pdf")
                .uploadDate(LocalDateTime.now())
                .seenDate(LocalDateTime.now().plusWeeks(1))
                .isValid(false)
                .build();
    }

    public static FileMetaDataInternshipManagerViewDto getFileMetaDataInternshipManagerViewDto() {
        return FileMetaDataInternshipManagerViewDto.builder()
                .id("90ksj30sak2")
                .userEmail("123456@gmail.com")
                .filename("CV.pdf")
                .build();
    }

    public static FileMetaDataStudentViewDto getFileMetaDataStudentViewDto() {
        return FileMetaDataStudentViewDto.builder()
                .filename("CV.pdf")
                .isValid(false)
                .uploadDate(LocalDateTime.now())
                .seenDate(LocalDateTime.now().plusWeeks(1))
                .build();
    }


}
