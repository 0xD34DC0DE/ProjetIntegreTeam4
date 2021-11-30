package com.team4.backend.service;

import com.team4.backend.dto.ProfileImageDto;
import com.team4.backend.model.ProfileImage;
import com.team4.backend.model.User;
import com.team4.backend.repository.ProfileImageRepository;
import com.team4.backend.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.bson.types.Binary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ProfileImageService {

    private final ProfileImageRepository profileImageRepository;

    private final UserRepository userRepository;

    public ProfileImageService(ProfileImageRepository profileImageRepository, UserRepository userRepository) {
        this.profileImageRepository = profileImageRepository;
        this.userRepository = userRepository;
    }

    public Mono<ProfileImage> uploadProfileImage(Mono<FilePart> image, String uploaderEmail) {
        return image
                .flatMap(filePart ->
                        filePart.content()
                                .flatMap(dataBuffer -> {
                                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(bytes);
                                    return Mono.just(bytes);
                                })
                                .last()
                                .flatMap(imageBytes ->
                                        createProfileImage(imageBytes, uploaderEmail, filePart.filename())
                                                .flatMap(profileImage ->
                                                        userRepository
                                                                .findByEmailAndUpdateProfileImageId(uploaderEmail, profileImage.getId()).thenReturn(profileImage)))
                );
    }

    public Flux<ProfileImageDto> getProfileImages(Set<String> uploadersEmails) {
        return userRepository
                .findByEmails(uploadersEmails)
                .map(User::getEmail)
                .collect(Collectors.toSet())
                .flatMapMany(userEmails ->
                        profileImageRepository
                                .findByUploaderEmails(userEmails)
                                .collectList()
                                .flatMapMany(profileImages ->
                                        Flux.zip(
                                                        Flux.fromIterable(profileImages),
                                                        Flux.fromIterable(profileImages)
                                                )
                                                .distinct().
                                                map(tuple -> {
                                                    String uploaderEmail = tuple.getT1().getUploaderEmail();
                                                    Binary profileImage = tuple.getT2().getImage();
                                                    return ProfileImageDto.profileImageDtoBuilder()
                                                            .image(profileImage.getData())
                                                            .uploaderEmail(uploaderEmail)
                                                            .build();
                                                })
                                )
                );

    }

    public Mono<byte[]> getProfileImage(String userEmail) {
        return userRepository
                .findByEmail(userEmail)
                .flatMap(user ->
                        profileImageRepository
                                .findById(user.getProfileImageId())
                                .map(profileImage -> profileImage.getImage().getData()));
    }

    public Mono<ProfileImage> createProfileImage(byte[] imageBytes, String uploaderEmail, String fileName) {
        return profileImageRepository.save(ProfileImage.profileImageBuilder()
                .uploaderEmail(uploaderEmail)
                .fileName(fileName)
                .image(new Binary(imageBytes))
                .build());
    }

}
