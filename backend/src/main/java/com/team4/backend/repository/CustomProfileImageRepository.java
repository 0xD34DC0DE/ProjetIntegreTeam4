package com.team4.backend.repository;

import com.team4.backend.model.ProfileImage;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface CustomProfileImageRepository {
    Flux<ProfileImage> findByUploaderEmails(Set<String> uploaderEmails);
}
