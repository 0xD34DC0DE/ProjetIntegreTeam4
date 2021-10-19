package com.team4.backend.controller;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.mapping.FileMetaDataMapper;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.service.FileMetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/file")
public class FileMetaDataController {

    @Autowired
    FileMetaDataService fileMetaDataService;

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public Mono<ResponseEntity<String>> uploadFile(@RequestPart("filename") String filename, @RequestPart("type") String type, @RequestPart("mimeType") String mimeType, @RequestPart("file") Mono<FilePart> filePartMono, Principal principal) {
        return fileMetaDataService.uploadFile(filename, type, mimeType, filePartMono, UserSessionService.getLoggedUserEmail(principal))
                .flatMap(u -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @GetMapping("/countAllInvalidCvNotSeen")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<Long> countAllInvalidCvNotSeen() {
        return fileMetaDataService.countAllInvalidCvNotSeen();
    }


    @GetMapping("/getListInvalidCvNotSeen/{noPage}")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Flux<FileMetaDataInternshipManagerViewDto> getListInvalidCvNotSeen(@PathVariable Integer noPage) throws InvalidPageRequestException {
        return fileMetaDataService.getListInvalidCvNotSeen(noPage).map(FileMetaDataMapper::toInternshipManagerViewDto);
    }

    @PatchMapping("/validateCv")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
    public Mono<ResponseEntity<String>> validateCv(@RequestParam("id") String id, @RequestParam("isValid") Boolean isValid) {
        return fileMetaDataService.validateCv(id, isValid)
                .flatMap(fileMetaData -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage())));
    }

}



















