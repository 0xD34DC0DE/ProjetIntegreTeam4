package com.team4.backend.mapping;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.testdata.FileMetaDataMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileMetaDataMapperTest {

    @Test
    void mapEntityToDto() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        //ACT
        FileMetaDataInternshipManagerViewDto fileMetaDataInternshipManagerViewDto = FileMetaDataMapper.toDto(fileMetaData);

        //ASSERT
        assertEquals(fileMetaData.getId(), fileMetaDataInternshipManagerViewDto.getId());
        assertEquals(fileMetaData.getUserEmail(), fileMetaDataInternshipManagerViewDto.getUserEmail());
        assertEquals(fileMetaData.getFilename(), fileMetaDataInternshipManagerViewDto.getFilename());
        assertEquals(fileMetaData.getCreationDate(), fileMetaDataInternshipManagerViewDto.getUploadDate());
    }
}
