package com.team4.backend.repository;

import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {UserRepository.class})
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void init() {
        Flux<User> users = Flux.just(
                User.userBuilder().email("testing_1@gmail.com").password("password1").isEnabled(true).role(Role.STUDENT).build(),
                User.userBuilder().email("testing_2@gmail.com").password("password2").isEnabled(false).role(Role.SUPERVISOR).build(),
                User.userBuilder().email("testing_3@gmail.com").password("password1").isEnabled(true).role(Role.STUDENT).build()
        );

        userRepository.saveAll(users).blockLast();
    }

    @Test
    void findByEmailAndPasswordAndIsEnabledTrue() {
        //ARRANGE
        String email1 = "testing_1@gmail.com";
        String password1 = "password1";

        String email2 = "testing_2@gmail.com";
        String password2 = "password2";

        String email3 = "non_existant_user@gmail.com";
        String password3 = "password3";

        //ACT
        Mono<User> userMono1 = userRepository.findByEmailAndPasswordAndIsEnabledTrue(email1, password1);
        Mono<User> userMono2 = userRepository.findByEmailAndPasswordAndIsEnabledTrue(email2, password2);
        Mono<User> userMono3 = userRepository.findByEmailAndPasswordAndIsEnabledTrue(email3, password3);

        //ASSERT
        StepVerifier.create(userMono1)
                .consumeNextWith(user -> assertEquals(email1, user.getEmail()))
                .verifyComplete();
        StepVerifier.create(userMono2).expectNextCount(0).verifyComplete();
        StepVerifier.create(userMono3).expectNextCount(0).verifyComplete();
    }

    @Test
    void existsByEmail() {
        //ARRANGE
        String email1 = "testing_1@gmail.com";
        String email2 = "non_existing_user@gmail.com";

        //ACT
        Mono<Boolean> booleanMono1 = userRepository.existsByEmail(email1);
        Mono<Boolean> booleanMono2 = userRepository.existsByEmail(email2);

        //ASSERT
        StepVerifier.create(booleanMono1)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();

        StepVerifier.create(booleanMono2)
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
    }

    @Test
    void findAllByRole(){
        // ACT
        Flux<User> userFlux = userRepository.findAllByRoleEquals("STUDENT");

        // ASSERT
        StepVerifier.create(userFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

}
