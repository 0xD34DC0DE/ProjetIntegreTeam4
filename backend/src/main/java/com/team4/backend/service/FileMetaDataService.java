package com.team4.backend.service;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.exception.FileDoNotExistException;
import com.team4.backend.mapping.FileMetaDataMapper;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.repository.FileMetaDataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class FileMetaDataService {

    private final FileMetaDataRepository fileMetaDataRepository;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    public Mono<Long> countAllInvalidCvNotSeen() {
        return fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse();
    }

    public Flux<FileMetaDataInternshipManagerViewDto> getListInvalidCvNotSeen(Integer noPage) {
        return fileMetaDataRepository.findAllByIsValidFalseAndIsSeenFalse(PageRequest.of(noPage, 10, Sort.by("uploadDate")))
                .map(FileMetaDataMapper::toInternshipManagerViewDto);
    }
    //Exclusive to internshipManager so wont be used any other service or role

    public Mono<FileMetaData> validateCv(String id, Boolean isValid) {
        return fileMetaDataRepository.findById(id)
                .switchIfEmpty(Mono.error(new FileDoNotExistException("This file do Not Exist")))
                .map(file -> {
                    file.setIsValid(isValid);
                    file.setIsSeen(true);
                    file.setSeenDate(LocalDateTime.now());
                    return file;
                }).flatMap(fileMetaDataRepository::save);
    }
}
