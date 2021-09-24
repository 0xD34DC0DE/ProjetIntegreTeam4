package com.team4.backend.repository;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.User;
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
                User.builder().registrationNumber("123456789").email("testing_1@gmail.com").password("password1").isEnabled(true).build(),
                User.builder().registrationNumber("423423432").email("testing_2@gmail.com").password("password2").isEnabled(false).build()
        );

        userRepository.saveAll(users).subscribe();
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
        // ARRANGE
        String email1 = "testing_1@gmail.com";
        String email2 = "non_existing_user@gmail.com";

        // ACT
        Mono<Boolean> booleanMono1 = userRepository.existsByEmail(email1);
        Mono<Boolean> booleanMono2 = userRepository.existsByEmail(email2);

        // ASSERT
        StepVerifier.create(booleanMono1)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();

        StepVerifier.create(booleanMono2)
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
    }
}
