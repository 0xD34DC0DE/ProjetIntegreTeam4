package com.team4.backend.controller;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.FileMetadata;
import com.team4.backend.service.FileAssetService;
import com.team4.backend.service.FileMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
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
    public Mono<String> storeAssetLocally(@RequestPart("file") Mono<FilePart> filePartMono) {
        return Mono.fromCallable(() -> File.createTempFile("projet-integre-team-4-", ".tmp"))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(tempFile -> filePartMono
                .flatMap(fp -> fp.transferTo(tempFile))
                .then(Mono.just(tempFile.getPath())));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public Mono<ServerResponse> pushAssetOnS3AndCreateMetadata(@RequestBody FileMetadata fileMetadata, Principal loggedUser) {
        // Store asset on S3 from the buffer created from a local file
        Mono<String> assetId = fileAssetService.create(fileMetadata.getPath(), fileMetadata.getUserEmail());
        final String metadataId = UUID.randomUUID().toString();
        return assetId.flatMap(id -> {
            // Squash given parameters to match the ones corresponding to the current user
            fileMetadata.setId(metadataId);
            fileMetadata.setUserEmail(loggedUser.getName());
            fileMetadata.setValidCV(false);
            fileMetadata.setPath(null);
            fileMetadata.setAssetId(id);

            return Mono.just(fileMetadata);
        }).flatMap(metadata -> fileMetadataService.create(fileMetadata))
        .map(FileMetadata::getId)
        .flatMap(id -> {
            try {
                return ServerResponse.created(new URI(metadataId)).build();
            } catch (URISyntaxException e) {
                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        });

//        try {
//            // The Flux system is going to complain about `block()`
//            // But there's no other way to not trigger the asynchronous metadata creation...
//            metadataId = createdMetadata.block().getId();
//        } catch (Exception ex) {
//            // TODO: enelver print
//            System.out.println("Don't care!");
//        }
//
//        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).location(new URI(metadataId)).build());
    }

}



















