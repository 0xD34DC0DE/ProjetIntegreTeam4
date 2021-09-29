package com.team4.backend.controller;

import com.team4.backend.model.FileMetaData;
import com.team4.backend.service.FileMetaDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fileMetaData")
public class FileMetaDataController {

    private final FileMetaDataService fileMetaDataService;

    public FileMetaDataController(FileMetaDataService fileMetaDataService) {
        this.fileMetaDataService = fileMetaDataService;
    }

    @GetMapping("/countAllInvalidCvNotSeen")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Mono<Long> countAllInvalidCvNotSeen(){
        return fileMetaDataService.countAllInvalidCvNotSeen();
    }

    @GetMapping("/getListInvalidCvNotSeen")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER')")
    public Flux<FileMetaData> getListInvalidCvNotSeen(){
        return fileMetaDataService.getListInvalidCvNotSeen();
    }




}
