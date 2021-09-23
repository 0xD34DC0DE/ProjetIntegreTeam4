package com.team4.backend.repository;

import com.team4.backend.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByRegistrationNumberAndPassword(String registrationNumber, String password);

    Mono<User> findByEmailAndPasswordAndIsEnabledTrue(String email, String password);

    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);
}
