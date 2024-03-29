package com.team4.backend.service;

import com.team4.backend.dto.NotificationDto;
import com.team4.backend.exception.FileNotFoundException;
import com.team4.backend.model.FileMetaData;
import com.team4.backend.model.Notification;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.NotificationType;
import com.team4.backend.model.enums.UploadType;
import com.team4.backend.repository.FileMetaDataRepository;
import com.team4.backend.util.ValidatingPageRequest;
import lombok.extern.java.Log;
import org.springframework.data.domain.Sort;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Log
@Service
public class FileMetaDataService {

    private final FileMetaDataRepository fileMetaDataRepository;

    private final StudentService studentService;

    private final FileAssetService fileAssetService;

    private final UserService userService;

    private final NotificationService notificationService;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository,
                               StudentService studentService,
                               FileAssetService fileAssetService,
                               UserService userService,
                               NotificationService notificationService) {
        this.fileMetaDataRepository = fileMetaDataRepository;
        this.studentService = studentService;
        this.fileAssetService = fileAssetService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public Mono<FileMetaData> create(FileMetaData fileMetadata) {
        return fileMetaDataRepository.save(fileMetadata);
    }

    protected File getTempFile() throws IOException {
        return File.createTempFile("projet-integre-team-4-", ".tmp");
    }

    protected String getUuid() {
        return UUID.randomUUID().toString();
    }


    public Mono<FileMetaData> uploadFile(String filename, String type, String mimeType, Mono<FilePart> filePartMono, String userEmail) {
        return Mono.fromCallable(this::getTempFile)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(tempFile -> filePartMono
                        .flatMap(fp -> fp.transferTo(tempFile)
                                // Hack to transform Mono<Void> into another Mono type since Void is treated the
                                // same as an empty Mono and it stops the chain.
                                // map -> changes the object signature (Mono<Void> -> Mono<File>)
                                .map(dummy -> tempFile)
                                // switchIfEmpty -> since the Mono<File> is still empty, create another one with
                                // the file inside
                                .switchIfEmpty(Mono.just(tempFile)))
                )
                .flatMap(tempFile -> {
                        studentService.setHasCvStatusTrue(userEmail);
                        return fileAssetService.create(tempFile.getPath(), userEmail, mimeType, getUuid())
                                    .flatMap(assetId -> Mono.just(FileMetaData.builder()
                                            .id(getUuid())
                                            .userEmail(userEmail)
                                            .isValid(false)
                                            .isSeen(false)
                                            .assetId(assetId)
                                            .type(UploadType.valueOf(type))
                                            .uploadDate(LocalDateTime.now())
                                            .filename(filename)
                                            .build()))
                                    .flatMap(this::create);
                });
    }

    public Mono<Long> countAllInvalidCvNotSeen() {
        return fileMetaDataRepository.countAllByIsValidFalseAndIsSeenFalse();
    }

    public Flux<FileMetaData> getListInvalidCvNotSeen(Integer noPage) {

        return ValidatingPageRequest
                .getPageRequestMono(noPage, 10, Sort.by("uploadDate").ascending())
                .flatMapMany(fileMetaDataRepository::findAllByIsValidFalseAndIsSeenFalse);

    }

    public Mono<FileMetaData> validateCv(String id, Boolean isValid, String rejectionExplanation) {
        return fileMetaDataRepository.findById(id)
                .switchIfEmpty(Mono.error(new FileNotFoundException("This file do Not Exist")))
                .map(file -> {
                    file.setIsValid(isValid);
                    file.setIsSeen(true);
                    file.setSeenDate(LocalDateTime.now());

                    if (isValid)
                        studentService.updateCvValidity(file.getUserEmail(), true).subscribe();
                    else
                        file.setRejectionExplanation(rejectionExplanation);
                    return file;
                }).flatMap(fileMetaDataRepository::save)
                .doOnSuccess(fileMetaData -> createCvValidationNotification(fileMetaData.getUserEmail(), fileMetaData.getIsValid(), fileMetaData.getRejectionExplanation()).subscribe());
    }

    public Mono<Notification> createCvValidationNotification(String userEmail, boolean isValid, String reason) {
        return userService
                .findByEmail(userEmail)
                .map(User::getId)
                .flatMap(studentId -> notificationService
                        .createNotification(
                                NotificationDto.notificationDtoBuilder()
                                        .receiverIds(Set.of(studentId))
                                        .data(null)
                                        .seenIds(Set.of())
                                        .title(isValid ? "Votre CV a été accepté" : "Votre CV a été refusé")
                                        .content(isValid ? "Appuyez pour consulter vos CV" : reason)
                                        .notificationType(NotificationType.SHOW_CV)
                                        .build()
                        ));
    }


    public Flux<FileMetaData> getAllCvByUserEmail(String userEmail) {
        return fileMetaDataRepository.findAllByUserEmail(userEmail)
                .switchIfEmpty(Mono.error(new FileNotFoundException("This file does not exist")));
    }

                public Mono<String> getLastValidatedCvWithUserEmail(String userEmail) {
        return fileMetaDataRepository.findAllByUserEmailAndIsValidTrueOrderByUploadDate(userEmail)
                .switchIfEmpty(Mono.error(new FileNotFoundException()))
                .collectList()
                .flatMap(files -> Mono.just(files.get(files.size() - 1).getAssetId()));
    }

}
