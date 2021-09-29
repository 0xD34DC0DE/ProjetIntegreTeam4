package com.team4.backend.service;

import com.team4.backend.model.FileMetaData;
import com.team4.backend.repository.FileMetaDataRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FileMetaDataService {

    private final FileMetaDataRepository fileMetaDataRepository;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    public Mono<Long> countAllInvalidCvNotSeen(){
        return fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse();
    }

    public Flux<FileMetaData> getListInvalidCvNotSeen(){
        return fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse();
    }
}
