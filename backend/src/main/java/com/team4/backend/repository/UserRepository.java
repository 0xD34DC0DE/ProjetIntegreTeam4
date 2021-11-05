package com.team4.backend.repository;

import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByEmailAndPasswordAndIsEnabledTrue(String email, String password);

    Mono<Boolean> existsByEmail(String email);

    public Flux<User> findAllByRoleEquals(String role);

    Mono<Long> deleteAllByRoleEquals(Role role);

    Mono<User> findByEmail(String userEmail);
}
