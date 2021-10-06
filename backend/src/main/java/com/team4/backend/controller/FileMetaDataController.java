package com.team4.backend.controller;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.mapping.FileMetaDataMapper;
import com.team4.backend.service.FileMetaDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Long> countAllInvalidCvNotSeen() {
        return fileMetaDataService.countAllInvalidCvNotSeen();
    }


    @GetMapping("/getListInvalidCvNotSeen/{noPage}")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<FileMetaDataInternshipManagerViewDto> getListInvalidCvNotSeen(@PathVariable Integer noPage) {
        return fileMetaDataService.getListInvalidCvNotSeen(noPage).map(FileMetaDataMapper::toInternshipManagerViewDto);
    }

    @PatchMapping("/validateCv")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<ResponseEntity<String>> validateCv(@RequestParam("id") String id, @RequestParam("isValid") Boolean isValid) {
        return fileMetaDataService.validateCv(id, isValid)
                .flatMap(fileMetaData -> Mono.just(ResponseEntity.ok().body("")))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage())));
    }


}
