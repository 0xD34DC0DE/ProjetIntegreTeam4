package com.team4.backend.repository;

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

import static org.junit.jupiter.api.Assertions.*;

@Log
@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes ={UserRepository.class})
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void init(){
        Flux<User> users = Flux.just(
                User.builder().registrationNumber("123456789").email("123456789@gmail.com").password("araa").build(),
                User.builder().registrationNumber("423423432").build()
        );

        userRepository.saveAll(users);

    }

    @Test
    void findByRegistrationNumberAndPassword(){
        //ARRANGE
        String registrationNumber1 = "123456789";
        String password1 = "araa";

        String registrationNumber2 = "4esdad";
        String password2 = "dsd2e32";

        //ACT
        Mono<User> userMono1 = userRepository.findByRegistrationNumberAndPassword(registrationNumber1,password1);
        Mono<User> userMono2 = userRepository.findByRegistrationNumberAndPassword(registrationNumber2,password2);

        //ASSERT

        userMono1.subscribe( user -> assertEquals(registrationNumber1,user.getRegistrationNumber()));
        userMono2.subscribe(Assertions::assertNull);
    }

    @Test
    void findByEmailAndPassword(){
        //ARRANGE
        String email1 = "123456789@gmail.com";
        String password1 = "araa";

        String email2 = "4esdad@gmail.com";
        String password2 = "dsd2e32";

        //ACT
        Mono<User> userMono1 = userRepository.findByEmailAndPassword(email1,password1);
        Mono<User> userMono2 = userRepository.findByEmailAndPassword(email2,password2);

        //ASSERT

        userMono1.subscribe( user -> assertEquals(email1,user.getEmail()));
        userMono2.subscribe(Assertions::assertNull);
    }


}
