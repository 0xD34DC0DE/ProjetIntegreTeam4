package com.team4.backend.repository;

import com.team4.backend.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface CustomUserRepository {

    Mono<User> findByEmailAndUpdateProfileImageId(String email, String profileImageId);

    Flux<User> findByEmails(Set<String> emails);

}
