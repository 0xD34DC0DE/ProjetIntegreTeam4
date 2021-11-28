package com.team4.backend.controller;

import com.team4.backend.service.ProfileImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

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
                                                          @RequestPart String uploaderId) {
        return profileImageService.uploadProfileImage(image, uploaderId).map(v -> ResponseEntity.ok(v.getId()));
   }

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','MONITOR', 'SUPERVISOR', 'STUDENT')")
    public Mono<ResponseEntity<byte[]>> getUploaderProfileImage(@RequestParam String userId){
        return profileImageService.getProfileImage(userId).map(ResponseEntity::ok);
    }

}
