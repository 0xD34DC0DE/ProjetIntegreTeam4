package com.team4.backend.controller;

import com.team4.backend.dto.ProfileImageDto;
import com.team4.backend.service.ProfileImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/profileImage")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    public ProfileImageController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','MONITOR', 'SUPERVISOR', 'STUDENT')")
    public Mono<ResponseEntity<String>> uploadProfileImage(@RequestPart Mono<FilePart> image,
                                                           @RequestPart String uploaderEmail) {
        return profileImageService.uploadProfileImage(image, uploaderEmail).map(v -> ResponseEntity.ok(v.getId()));
    }

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','MONITOR', 'SUPERVISOR', 'STUDENT')")
    public Mono<ResponseEntity<byte[]>> getUploaderProfileImage(@RequestParam String uploaderEmail) {
        return profileImageService.getProfileImage(uploaderEmail).map(ResponseEntity::ok);
    }

    @PostMapping(path = "/emails")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','MONITOR', 'SUPERVISOR', 'STUDENT')")
    public Mono<List<ProfileImageDto>> getUploadersProfileImages(@RequestBody Set<String> uploadersEmails) {
        return profileImageService.getProfileImages(uploadersEmails).collectList();
    }

}
