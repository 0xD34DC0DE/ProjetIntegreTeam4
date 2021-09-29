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

    public Mono<String> create(FileMetadata fileMetadata) {
        Mono<FileMetadata> data = fileMetadataRepository.save(fileMetadata);

        String id = data.block().getId();

        return Mono.just(id);
    }

    public Mono<FileMetadata> get(String id) {
        return fileMetadataRepository.findById(id);
    }
}
