package com.team4.backend.mapping;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.dto.FileMetaDataStudentViewDto;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.testdata.FileMetaDataMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileMetaDataMapperTest {

    @Test
    void mapEntityToInternshipManagerViewDto() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        //ACT
        FileMetaDataInternshipManagerViewDto fileMetaDataInternshipManagerViewDto = FileMetaDataMapper.toInternshipManagerViewDto(fileMetaData);

        //ASSERT
        assertEquals(fileMetaData.getId(),fileMetaDataInternshipManagerViewDto.getId());
        assertEquals(fileMetaData.getAssetId(), fileMetaDataInternshipManagerViewDto.getAssetId());
        assertEquals(fileMetaData.getUserEmail(), fileMetaDataInternshipManagerViewDto.getUserEmail());
        assertEquals(fileMetaData.getFilename(), fileMetaDataInternshipManagerViewDto.getFilename());
        assertEquals(fileMetaData.getUploadDate(), fileMetaDataInternshipManagerViewDto.getUploadDate());
    }

    @Test
    void getFileMetaDataStudentViewDto() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        //ACT
        FileMetaDataStudentViewDto fileMetaDataStudentViewDto = FileMetaDataMapper.toStudentViewDto(fileMetaData);

        //ASSERT
        assertEquals(fileMetaData.getFilename(), fileMetaDataStudentViewDto.getFilename());
        assertEquals(fileMetaData.getIsValid(), fileMetaDataStudentViewDto.getIsValid());
        assertEquals(fileMetaData.getUploadDate(), fileMetaDataStudentViewDto.getUploadDate());
        assertEquals(fileMetaData.getSeenDate(), fileMetaDataStudentViewDto.getSeenDate());
    }
    
}
