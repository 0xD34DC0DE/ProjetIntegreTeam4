package com.team4.backend.controller;

import com.team4.backend.model.FileMetadata;
import com.team4.backend.model.enums.FileType;
import com.team4.backend.service.FileAssetService;
import com.team4.backend.service.FileMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

@RestController
@RequestMapping("/file")
public class FileMetadataController {

    @Autowired
    FileMetadataService fileMetadataService;

    @Autowired
    FileAssetService fileAssetService;

    @PostMapping
    public Mono<ResponseEntity<Void>> create(FileMetadata fileMetadata) throws URISyntaxException {
        Mono<String> id = fileMetadataService.create(fileMetadata);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).location(new URI(id.block())).build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FileMetadata>> get(@PathVariable("id") String id) {
        FileMetadata fileMetadata = fileMetadataService.get(id).block();
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(fileMetadata));
    }

    @PostMapping(
            value = "/asset",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
//    public Mono<ResponseEntity<Void>> createWithAsset(@RequestPart("type") String type, @RequestPart("file") MultipartFile file, Principal loggedUser) throws URISyntaxException, IOException {
    public Mono<ResponseEntity<Void>> createWithAsset(@RequestPart("file") MultipartFile file, Principal loggedUser) throws URISyntaxException, IOException {

        try {

            String assetId = fileAssetService.create(file, loggedUser.getName());

            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setUserId(loggedUser.getName());
            fileMetadata.setFilename(file.getOriginalFilename());
            fileMetadata.setValidCV(false);
            fileMetadata.setType(FileType.valueOf("CV"));
            fileMetadata.setAssetId(assetId);

            return create(fileMetadata);
        } catch (Exception e) {
            System.out.println("here" + e.getMessage());
        }

        return null;
    }
}



















