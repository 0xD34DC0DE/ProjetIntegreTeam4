package com.team4.backend.service;

import com.team4.backend.model.FileMetadata;
import com.team4.backend.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class FileMetadataService {

    @Autowired
    FileMetadataRepository fileMetadataRepository;

    public Mono<FileMetadata> create(FileMetadata fileMetadata) {
        Mono<FileMetadata> data = fileMetadataRepository.save(fileMetadata);

        return data;
    }

    public Mono<FileMetadata> get(String id) {
        return fileMetadataRepository.findById(id);
    }
}
