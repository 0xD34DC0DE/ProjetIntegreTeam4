package com.team4.backend.repository;

import com.team4.backend.model.FileMetaData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;

@Repository
public interface FileMetaDataRepository extends ReactiveMongoRepository<FileMetaData, String> {
    Flux<FileMetaData> findFileMetaDataById(String id);

    Mono<Long> countAllByIsValidFalseAndIsSeenFalse();

    Flux<FileMetaData> findAllByIsValidFalseAndIsSeenFalse(Pageable pageable);

    Flux<FileMetaData> findAllByUserEmail(String userEmail);

}
