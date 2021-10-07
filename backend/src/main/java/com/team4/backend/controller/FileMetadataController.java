package com.team4.backend.controller;

import com.team4.backend.service.FileMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/file")
public class FileMetadataController {

    @Autowired
    FileMetadataService fileMetadataService;

    protected String getLoggedUserName(Principal loggedUser) {
        //
        if (loggedUser == null) {
            return "";
        }
        return loggedUser.getName();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public Mono<ResponseEntity<Void>> uploadFile(@RequestPart("filename") String filename, @RequestPart("type") String type, @RequestPart("mimeType") String mimeType, @RequestPart("file") Mono<FilePart> filePartMono, Principal loggedUser) {
        return fileMetadataService.uploadFile(filename, type, mimeType, filePartMono, getLoggedUserName(loggedUser));
    }

}



















