package com.team4.backend.repository;

import com.team4.backend.model.User;
import reactor.core.publisher.Mono;

public interface CustomUserRepository {

    Mono<User> findByIdAndUpdateProfileImageId(String id, String profileImageId);

}
