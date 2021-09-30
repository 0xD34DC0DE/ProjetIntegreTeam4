package com.team4.backend.service;

import com.team4.backend.model.FileMetaData;
import com.team4.backend.repository.FileMetaDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FileMetaDataService {

    private final FileMetaDataRepository fileMetaDataRepository;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    public Mono<Long> countAllInvalidCvNotSeen() {
        return fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse();
    }

    public Flux<FileMetaData> getListInvalidCvNotSeen() {
        return fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse();
    }

    public Mono<FileMetaData> validateCv(String id, Boolean isValid) {

        return fileMetaDataRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find cv")))
                .map(file -> {
                    file.setIsValid(isValid);
                    file.setIsSeen(true);
                    return file;
                }).flatMap(fileMetaDataRepository::save);
    }
}
