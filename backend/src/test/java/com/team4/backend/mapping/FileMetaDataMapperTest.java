package com.team4.backend.mapping;

import com.team4.backend.dto.FileMetaDataDto;
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
        FileMetaDataDto fileMetaDataDto = FileMetaDataMapper.toDto(fileMetaData);

        //ASSERT
        assertEquals(fileMetaData.getId(), fileMetaDataDto.getId());
        assertEquals(fileMetaData.getUserEmail(), fileMetaDataDto.getUserEmail());
        assertEquals(fileMetaData.getFilename(), fileMetaDataDto.getFilename());
        assertEquals(fileMetaData.getIsValid(), fileMetaDataDto.getIsValid());
        assertEquals(fileMetaData.getCreationDate(), fileMetaDataDto.getCreationDate());
        assertEquals(fileMetaData.getSeenDate(), fileMetaDataDto.getSeenDate());
    }
}
