package com.team4.backend.service;

import com.team4.backend.model.ProfileImage;
import com.team4.backend.repository.ProfileImageRepository;
import com.team4.backend.repository.UserRepository;
import org.bson.types.Binary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProfileImageService {

    private final ProfileImageRepository profileImageRepository;

    private final UserRepository userRepository;

    public ProfileImageService(ProfileImageRepository profileImageRepository, UserRepository userRepository) {
        this.profileImageRepository = profileImageRepository;
        this.userRepository = userRepository;
    }

    public Mono<ProfileImage> uploadProfileImage(Mono<FilePart> image, String uploaderId) {
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
                                        createProfileImage(imageBytes, uploaderId, filePart.filename())
                                                .flatMap(profileImage ->
                                                        userRepository
                                                                .findByIdAndUpdateProfileImageId(uploaderId, profileImage.getId()).thenReturn(profileImage)))
                );
    }

    public Mono<byte[]> getProfileImage(String userId) {
        return userRepository
                .findById(userId)
                .flatMap(user ->
                        profileImageRepository
                                .findById(user.getProfileImageId())
                                .map(profileImage -> profileImage.getImage().getData()));
    }

    public Mono<ProfileImage> createProfileImage(byte[] imageBytes, String uploaderId, String fileName) {
        return profileImageRepository.save(ProfileImage.profileImageBuilder()
                .uploaderId(uploaderId)
                .fileName(fileName)
                .image(new Binary(imageBytes))
                .build());
    }

}
