package com.team4.backend.service;

import com.team4.backend.model.FileMetadata;
import com.team4.backend.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Objects;

@Service
public class FileMetadataService {

    @Autowired
    FileMetadataRepository fileMetadataRepository;

    public Mono<FileMetadata> create(FileMetadata fileMetadata) {
        return fileMetadataRepository.save(fileMetadata);
    }

    public Mono<FileMetadata> get(String id) {
        return fileMetadataRepository.findById(id);
    }
}
