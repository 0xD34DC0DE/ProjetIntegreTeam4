package com.team4.backend.service;

import com.team4.backend.model.FileMetadata;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.FileType;
import com.team4.backend.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileMetadataService {

    @Autowired
    FileMetadataRepository fileMetadataRepository;

    @Autowired
    FileAssetService fileAssetService;

    public Mono<FileMetadata> create(FileMetadata fileMetadata) {
        return fileMetadataRepository.save(fileMetadata);
    }

    protected File getTempFile() throws IOException {
        return File.createTempFile("projet-integre-team-4-", ".tmp");
    }

    protected String getUuid() {
        return UUID.randomUUID().toString();
    }

    public Mono<ResponseEntity<Void>> uploadFile(String filename, String type, String mimeType, Mono<FilePart> filePartMono, String userEmail) {
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
                                .flatMap(assetId -> Mono.just(FileMetadata.builder()
                                        .id(getUuid())
                                        .userEmail(userEmail)
                                        .validCV(false)
                                        .assetId(assetId)
                                        .type(FileType.valueOf(type))
                                        .creationDate(LocalDateTime.now())
                                        .filename(filename)
                                        .build()))
                                .flatMap(this::createMetadata));
    }

    public Mono<ResponseEntity<Void>> createMetadata(FileMetadata fileMetadata) {
        return create(fileMetadata)
                .map(metadata -> {
                    try {
                        return ResponseEntity.created(new URI(metadata.getId())).build();
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                });
    }
}
