package com.team4.backend.controller;

import com.team4.backend.model.FileMetadata;
import com.team4.backend.model.enums.FileType;
import com.team4.backend.service.FileAssetService;
import com.team4.backend.service.FileMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileMetadataController {

    @Autowired
    FileMetadataService fileMetadataService;

    @Autowired
    FileAssetService fileAssetService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FileMetadata>> get(@PathVariable("id") String id) {

        FileMetadata fileMetadata = fileMetadataService.get(id).block();
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(fileMetadata));
    }

    @PostMapping("/asset")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public Mono<ResponseEntity<Void>> storeAssetLocally(@RequestPart("filename") String filename, @RequestPart("type") String type, @RequestPart("file") Mono<FilePart> filePartMono, Principal loggedUser) {
        return Mono.fromCallable(() ->
                File.createTempFile("projet-integre-team-4-", ".tmp"))
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
                        fileAssetService.create(tempFile.getPath(), loggedUser.getName())
                                .flatMap(assetId -> Mono.just(FileMetadata.builder()
                                        .id(UUID.randomUUID().toString())
                                        .userEmail(loggedUser.getName())
                                        .validCV(false)
                                        .assetId(assetId)
                                        .type(FileType.valueOf(type))
                                        .creationDate(LocalDateTime.now())
                                        .filename(filename)
                                        .build()))
                                .flatMap(fileMetadata1 -> pushAssetOnS3AndCreateMetadata(fileMetadata1, loggedUser)));
    }

    public Mono<ResponseEntity<Void>>  pushAssetOnS3AndCreateMetadata(@RequestBody FileMetadata fileMetadata, Principal loggedUser) {
        // Store asset on S3 from the buffer created from a local file
        return fileMetadataService.create(fileMetadata)
                .map(metadata -> {
                    try {
                        return ResponseEntity.created(new URI(metadata.getId())).build();
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                });
    }

}



















