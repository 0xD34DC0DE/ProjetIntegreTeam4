package com.team4.backend.controller;

import com.team4.backend.model.FileMetadata;
import com.team4.backend.service.FileAssetService;
import com.team4.backend.service.FileMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
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
    public Mono<String> storeAssetLocally(@RequestPart("file") Mono<FilePart> filePartMono) throws IOException {
        File tempFile = File.createTempFile("projet-integre-team-4-", ".tmp");
        return  filePartMono
                .flatMap(fp -> fp.transferTo(tempFile))
                .then(Mono.just(tempFile.getPath()));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public Mono<ResponseEntity<Void>> pushAssetOnS3AndCreateMetadata(@RequestBody FileMetadata fileMetadata, Principal loggedUser) throws URISyntaxException, IOException {
        // Store asset on S3 from the buffer created from a local file
        Mono<String> assetId = fileAssetService.create(fileMetadata.getPath(), fileMetadata.getUserEmail());

        // Squash given parameters to match the ones corresponding to the current user
        String metadataId = UUID.randomUUID().toString();
        fileMetadata.setId(metadataId);
        fileMetadata.setUserEmail(loggedUser.getName());
        fileMetadata.setValidCV(false);
        fileMetadata.setPath(null);
        fileMetadata.setAssetId(assetId.block());

        // Persist the new metadata in mongodb
        Mono<FileMetadata> createdMetadata = fileMetadataService.create(fileMetadata);

        try {
            // The Flux system is going to complain about `block()`
            // But there's no other way to not trigger the asynchronous metadata creation...
            metadataId = createdMetadata.block().getId();
        } catch (Exception ex) {
            // TODO:
            System.out.println("Don't care!");
        }

        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).location(new URI(metadataId)).build());
    }

}



















