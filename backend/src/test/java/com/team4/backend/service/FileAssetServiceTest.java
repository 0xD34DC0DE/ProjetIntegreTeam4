package com.team4.backend.service;

import com.team4.backend.repository.FileAssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class FileAssetServiceTest {

    @Mock
    FileAssetRepository fileAssetRepository;

    @InjectMocks
    FileAssetService fileAssetService;

    private final String wrongFilepath = "/wrongFilepath";
    private final String filepath = "/filepath";
    private final String userEmail = "test@gmail.com";
    private final String mimeType = "application.pdf";

    private String location;
    private FileInputStream fileInputStream;
    private Map<String, String> metadata;

    private String uuid = "uuidForTests";

    @BeforeEach
    void setUp() {
        fileInputStream = mock(FileInputStream.class);

        metadata = new HashMap<>();
        metadata.put(HttpHeaders.CONTENT_TYPE, mimeType);

        location = userEmail + "/" + uuid;
    }


    @Test
    void shouldCreate() throws FileNotFoundException {
        //ARRANGE
        FileAssetService fileAssetServiceSpy = spy(fileAssetService);
        doReturn(fileInputStream).when(fileAssetServiceSpy).getFileInputStream(filepath);

        //ACT
        Mono<String> response = fileAssetServiceSpy.create(filepath, userEmail, mimeType, uuid);

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(location, s);
        }).verifyComplete();
    }


    @Test
    void shouldNotCreateFailForWrongFile() {
        assertThrows(FileNotFoundException.class, () -> fileAssetService.getFileInputStream(wrongFilepath));
    }


}