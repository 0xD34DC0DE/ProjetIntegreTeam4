package com.team4.backend.controller;

import com.team4.backend.dto.FileMetaDataInternshipManagerViewDto;
import com.team4.backend.dto.FileMetaDataStudentViewDto;
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
    public Mono<ResponseEntity<String>> uploadFile(@RequestPart("filename") String filename,
                                                   @RequestPart("type") String type, @RequestPart("mimeType") String mimeType,
                                                   @RequestPart("file") Mono<FilePart> filePartMono, Principal principal) {
        return fileMetaDataService
                .uploadFile(filename, type, mimeType, filePartMono, UserSessionService.getLoggedUserEmail(principal))
                .flatMap(u -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("")));
    }

    @GetMapping("/countAllInvalidCvNotSeen")
    @PreAuthorize("hasAuthority('INTERNSHIP_MANAGER')")
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
    public Mono<ResponseEntity<String>> validateCv(@RequestParam("id") String id,
                                                   @RequestParam("isValid") Boolean isValid,
                                                   @RequestBody(required = false) String rejectionExplanation) {
        return fileMetaDataService.validateCv(id, isValid, rejectionExplanation)
                .flatMap(fileMetaData -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body("")));
    }

<<<<<<< HEAD
    @GetMapping("/getAllCvByUserEmail/{userEmail}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Flux<FileMetaDataStudentViewDto> getAllCvByUserEmail(@PathVariable String userEmail) {
        return fileMetaDataService.getAllCvByUserEmail(userEmail).map(FileMetaDataMapper::toStudentViewDto);
    }

=======
    @GetMapping("/getLatestCv/{studentEmail}")
    @PreAuthorize("hasAuthority('MONITOR')")
    public Mono<String> getFirstValidCv(@PathVariable String studentEmail) {
        return fileMetaDataService.getLastValidatedCvWithUserEmail(studentEmail);
    }
>>>>>>> b08952a42262e211490815b59452ecdb09ec8706
}
