package com.team4.backend.service;

import com.team4.backend.exception.FileDoNotExistException;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.model.enums.UploadType;
import com.team4.backend.repository.FileMetaDataRepository;
import com.team4.backend.util.ValidatingPageRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Log
@Service
public class FileMetaDataService {

    @Autowired
    FileMetaDataRepository fileMetaDataRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    FileAssetService fileAssetService;

    public Mono<FileMetaData> create(FileMetaData fileMetadata) {
        return fileMetaDataRepository.save(fileMetadata);
    }

    protected File getTempFile() throws IOException {
        return File.createTempFile("projet-integre-team-4-", ".tmp");
    }

    protected String getUuid() {
        return UUID.randomUUID().toString();
    }


    public Mono<FileMetaData> uploadFile(String filename, String type, String mimeType, Mono<FilePart> filePartMono, String userEmail) {
        System.out.println("dans service");
        return Mono.fromCallable(this::getTempFile)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(tempFile -> filePartMono
                        .flatMap(fp -> fp.transferTo(tempFile)
                                // Hack to transform Mono<Void> into another Mono type since Void is treated the
                                // same as an empty Mono and it stops the chain.
                                // map -> changes the object signature (Mono<Void> -> Mono<File>)
                                .map(dummy -> tempFile)
                                // switchIfEmpty -> since the Mono<File> is still empty, create another one with
                                // the file inside
                                .switchIfEmpty(Mono.just(tempFile)))
                )
                .flatMap(tempFile ->
                        fileAssetService.create(tempFile.getPath(), userEmail, mimeType, getUuid())
                                .flatMap(assetId -> Mono.just(FileMetaData.builder()
                                        .id(getUuid())
                                        .userEmail(userEmail)
                                        .isValid(false)
                                        .isSeen(false)
                                        .assetId(assetId)
                                        .type(UploadType.valueOf(type))
                                        .uploadDate(LocalDateTime.now())
                                        .filename(filename)
                                        .build()))
                                .flatMap(this::create));
    }

    public Mono<Long> countAllInvalidCvNotSeen() {
        return fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse();
    }

    public Flux<FileMetaData> getListInvalidCvNotSeen(Integer noPage) throws InvalidPageRequestException {

        return fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse(ValidatingPageRequest.getPageRequest(noPage, 10, Sort.by("uploadDate").ascending()));
    }

    public Mono<FileMetaData> validateCv(String id, Boolean isValid) {
        return fileMetaDataRepository.findById(id)
                .switchIfEmpty(Mono.error(new FileDoNotExistException("This file do Not Exist")))
                .map(file -> {
                    file.setIsValid(isValid);
                    file.setIsSeen(true);
                    file.setSeenDate(LocalDateTime.now());

                    if (isValid)
                        studentService.updateCvValidity(file.getUserEmail(), true).subscribe();

                    return file;
                }).flatMap(fileMetaDataRepository::save);
    }
    
}
