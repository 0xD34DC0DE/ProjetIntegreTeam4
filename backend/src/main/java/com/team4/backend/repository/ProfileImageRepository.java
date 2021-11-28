package com.team4.backend.repository;

import com.team4.backend.model.ProfileImage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileImageRepository extends ReactiveMongoRepository<ProfileImage, String> {

    Mono<ProfileImage> findByUploaderId(String uploaderId);

}
