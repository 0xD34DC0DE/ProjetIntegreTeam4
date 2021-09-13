package com.team4.backend.repository;

import com.team4.backend.model.User;
import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.util.PBKDF2Encoder;
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

import static org.junit.jupiter.api.Assertions.*;

@Log
@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes ={UserRepository.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableAutoConfiguration
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void init(){
        Flux<User> users = Flux.just(
                User.builder().registrationNumber("123456789").password("araa").build(),
                User.builder().registrationNumber("423423432").build()
        );

        userRepository.saveAll(users);

    }

    @Test
    void findByRegistrationNumberAndPassword(){
        //ARRANGE
        AuthRequest authRequest1 = new AuthRequest("123456789","araa");
        AuthRequest authRequest2 = new AuthRequest("4esdad","dsd2e32");

        //ACT
        Mono<User> userMono1 = userRepository.findByRegistrationNumberAndPassword(authRequest1.getRegistrationNumber(),authRequest1.getPassword());
        Mono<User> userMono2 = userRepository.findByRegistrationNumberAndPassword(authRequest2.getRegistrationNumber(),authRequest2.getPassword());

        //ASSERT

        userMono1.subscribe( user -> assertEquals(authRequest1.getRegistrationNumber(),user.getRegistrationNumber()));
        userMono2.subscribe(Assertions::assertNull);
    }


}
