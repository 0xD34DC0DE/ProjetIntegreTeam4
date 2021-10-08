package com.team4.backend.repository;

import com.team4.backend.model.FileMetadata;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FileMetadataRepository extends ReactiveMongoRepository<FileMetadata, String> {
    Flux<FileMetadata> findFileMetadataById(String id);
}
