package com.team4.backend.service;

import com.team4.backend.exception.FileDoNotExistException;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.UploadType;
import com.team4.backend.repository.FileMetaDataRepository;
import com.team4.backend.testdata.FileMetaDataMockData;
import com.team4.backend.testdata.StudentMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
class FileMetaDataServiceTest {

    @Mock
    FileMetaDataRepository fileMetaDataRepository;

    @Mock
    StudentService studentService;

    @Mock
    FileAssetService fileAssetService;

    @InjectMocks
    FileMetaDataService fileMetaDataService;

    private final String filepath = "/filepath";
    private final String userEmail = "test@gmail.com";
    private final String mimeType = "application/pdf";

    private final String filename = "filename";
    private final UploadType type = UploadType.CV;

    private String location = "/location";

    private FilePart filePart;

    private File tempFile;

    private FileMetaData fileMetadata;

    private final String uuid = "uuidForTests";

    @BeforeEach
    void setUp() {
        fileMetadata = new FileMetaData();
        fileMetadata.setId("id");
        fileMetadata.setUserEmail("userEmail");
        fileMetadata.setFilename("filename");
        fileMetadata.setAssetId("assetId");
        fileMetadata.setIsValid(false);
        fileMetadata.setType(type);
        fileMetadata.setUploadDate(LocalDateTime.MIN);

        filePart = mock(FilePart.class);
        tempFile = mock(File.class);

        location = userEmail + "/" + uuid;
    }

    @Test
    void shouldCreate() {
        //ARRANGE
        when(fileMetaDataRepository.save(fileMetadata)).thenReturn(Mono.just(fileMetadata));

        //ACT
        Mono<FileMetaData> response = fileMetaDataService.create(fileMetadata);

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertNotNull(s.getId());
        }).verifyComplete();

    }

    @Test
    void shouldUploadFile() throws  IOException {
        //ARRANGE
        when(fileAssetService.create(filepath, userEmail, mimeType, uuid)).thenReturn(Mono.just(location));

        when(filePart.transferTo(any(File.class))).thenReturn(Mono.empty().then());


        FileMetaDataService fileMetaDataServiceSpy = spy(fileMetaDataService);
        when(fileMetaDataServiceSpy.getTempFile()).thenReturn(tempFile);
        when(tempFile.getPath()).thenReturn(filepath);

        when(fileMetaDataServiceSpy.getUuid()).thenReturn(uuid);

        ArgumentCaptor<FileMetaData> fileMetadataCaptor = ArgumentCaptor.forClass(FileMetaData.class);
        when(fileMetaDataRepository.save(fileMetadataCaptor.capture())).thenReturn(Mono.just(fileMetadata));

        //ACT
        Mono<FileMetaData> fileMetaDataMono = fileMetaDataServiceSpy.uploadFile(filename, type.toString(), mimeType, Mono.just(filePart), userEmail);

        //ASSERT
        StepVerifier.create(fileMetaDataMono).consumeNextWith(Assertions::assertNotNull).verifyComplete();

        assertEquals(uuid, fileMetadataCaptor.getValue().getId());
        assertEquals(userEmail, fileMetadataCaptor.getValue().getUserEmail());
    }

    @Test
    void shouldCountAllInvalidCvNotSeen() {
        //ARRANGE
        Mono<Long> nbrOfInvalidCvNotSeen = Mono.just(5L);

        when(fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse()).thenReturn(nbrOfInvalidCvNotSeen);

        //ACT
        Mono<Long> nbrOfInvalidCvNotSeenReturned = fileMetaDataService.countAllInvalidCvNotSeen();

        //ASSERT
        StepVerifier.create(nbrOfInvalidCvNotSeenReturned)
                .assertNext(n -> Assertions.assertEquals(5L, n))
                .verifyComplete();
    }

    @Test
    void shouldGetListInvalidCvNotSeen() {
        //ARRANGE
        Integer noPage = 0;
        when(fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse(PageRequest.of(noPage, 10, Sort.by("uploadDate").ascending())))
                .thenReturn(Flux.just(
                        FileMetaData.builder().build(),
                        FileMetaData.builder().build(),
                        FileMetaData.builder().build()
                ));

        //ACT
        Flux<FileMetaData> fileMetaDataDtoFlux = fileMetaDataService.getListInvalidCvNotSeen(noPage);

        //ASSERT
        StepVerifier.create(fileMetaDataDtoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void shouldValidateCv() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();
        Student student = StudentMockData.getMockStudent();

        when(fileMetaDataRepository.findById(any(String.class))).thenReturn(Mono.just(fileMetaData));
        when(studentService.updateCvValidity(fileMetaData.getUserEmail(), true)).thenReturn(Mono.just(student));
        when(fileMetaDataRepository.save(any(FileMetaData.class))).thenReturn(Mono.just(fileMetaData));

        //ACT
        Mono<FileMetaData> fileMetaDataMono = fileMetaDataService.validateCv(fileMetaData.getId(), true);

        //ASSERT
        StepVerifier.create(fileMetaDataMono)
                .assertNext(f -> {
                    Assertions.assertTrue(f.getIsSeen());
                    Assertions.assertTrue(f.getIsValid());
                }).verifyComplete();
    }

    @Test
    void shouldNotValidateCv() {
        //ARRANGE
        FileMetaData fileMetaData = FileMetaDataMockData.getFileMetaData();

        when(fileMetaDataRepository.findById(any(String.class))).thenReturn(Mono.empty());

        //ACT
        Mono<FileMetaData> fileMetaDataMono = fileMetaDataService.validateCv(fileMetaData.getId(), true);

        //ASSERT
        StepVerifier.create(fileMetaDataMono)
                .expectError(FileDoNotExistException.class)
                .verify();
    }

}
