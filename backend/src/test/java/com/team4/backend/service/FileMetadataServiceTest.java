package com.team4.backend.service;

import com.team4.backend.model.FileMetadata;
import com.team4.backend.model.enums.FileType;
import com.team4.backend.repository.FileMetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class FileMetadataServiceTest {

    @Mock
    FileMetadataRepository fileMetadataRepository;

    @Mock
    FileAssetService fileAssetService;

    @InjectMocks
    FileMetadataService fileMetadataService;

    private final String filepath = "/filepath";
    private final String userEmail = "test@gmail.com";
    private final String mimeType = "application.pdf";

    private final String filename = "filename";
    private final FileType type = FileType.CV;

    private String location = "/tmp";

    private FilePart filePart;

    private File tempFile;

    private FileMetadata fileMetadata;

    private final String uuid = "uuidForTests";

    @BeforeEach
    void setUp() {
        fileMetadata = new FileMetadata();
        fileMetadata.setId("id");
        fileMetadata.setUserEmail("userEmail");
        fileMetadata.setFilename("filename");
        fileMetadata.setAssetId("assetId");
        fileMetadata.setIsValid(false);
        fileMetadata.setType(type);
        fileMetadata.setCreationDate(LocalDateTime.MIN);

        filePart = mock(FilePart.class);
        tempFile = mock(File.class);

        location = userEmail + "/" + uuid;
    }

    @Test
    void create() {
        //ARRANGE
        when(fileMetadataRepository.save(fileMetadata)).thenReturn(Mono.just(fileMetadata));

        //ACT
        Mono<FileMetadata> response = fileMetadataService.create(fileMetadata);

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertNotNull(s.getId());
        }).verifyComplete();

    }

    @Test
    void uploadFile() throws NoSuchMethodException, IOException {
        //ARRANGE
        when(fileAssetService.create(filepath, userEmail, mimeType, uuid)).thenReturn(Mono.just(location));

        when(filePart.transferTo(any(File.class))).thenReturn(Mono.empty().then());


        FileMetadataService fileMetadataServiceSpy = spy(fileMetadataService);
        when(fileMetadataServiceSpy.getTempFile()).thenReturn(tempFile);
        when(tempFile.getPath()).thenReturn(filepath);

        when(fileMetadataServiceSpy.getUuid()).thenReturn(uuid);

        ArgumentCaptor<FileMetadata> fileMetadataCaptor = ArgumentCaptor.forClass(FileMetadata.class);
        when(fileMetadataRepository.save(fileMetadataCaptor.capture())).thenReturn(Mono.just(fileMetadata));

        //ACT
        Mono<ResponseEntity<Void>> response = fileMetadataServiceSpy.uploadFile(filename, type.toString(), mimeType, Mono.just(filePart), userEmail);

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(HttpStatus.CREATED, s.getStatusCode());
        }).verifyComplete();

        assertEquals(uuid, fileMetadataCaptor.getValue().getId());
        assertEquals(userEmail, fileMetadataCaptor.getValue().getUserEmail());
    }

    @Test
    void shouldCreateMetadata() {
        //ARRANGE
        when(fileMetadataRepository.save(fileMetadata)).thenReturn(Mono.just(fileMetadata));

        //ACT
        Mono<ResponseEntity<Void>> response = fileMetadataService.createMetadata(fileMetadata);

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(HttpStatus.CREATED, s.getStatusCode());
        }).verifyComplete();
    }

    @Test
    void shouldNotCreateMetadata() {
        //ARRANGE
        fileMetadata.setId("invalid URI");
        when(fileMetadataRepository.save(fileMetadata)).thenReturn(Mono.just(fileMetadata));

        //ACT
        Mono<ResponseEntity<Void>> response = fileMetadataService.createMetadata(fileMetadata);

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, s.getStatusCode());
        }).verifyComplete();
    }
}