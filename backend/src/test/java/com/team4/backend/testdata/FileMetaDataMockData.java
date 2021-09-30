package com.team4.backend.testdata;

import com.team4.backend.model.FileMetaData;

public abstract class FileMetaDataMockData {

    public static FileMetaData getFileMetaData(){
        return FileMetaData.builder()
                .id("90ksj30sak2")
                .userId("7dah39a03hd")
                .filename("CV.pdf")
                .build();
    }

}
