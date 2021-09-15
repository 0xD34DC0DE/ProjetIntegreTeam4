package com.team4.backend.service;

import com.team4.backend.model.User;
import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.JwtUtil;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PBKDF2Encoder pbkdf2Encoder;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    UserService userService;

    @Test
    void login() {
        //ARRANGE
        User user1 = User.builder().registrationNumber("123456789").email("123456789@gmail.com").password("araa").build();
        AuthRequest authRequest1 = new AuthRequest(user1.getEmail(),user1.getPassword());
        String token = "adasdadadsas";

        when(userRepository.findByEmailAndPassword(user1.getEmail(), pbkdf2Encoder.encode(user1.getPassword()))).thenReturn(Mono.just(user1));
        when(jwtUtil.generateToken(user1)).thenReturn("adasdadadsas");


        User user2 = User.builder().registrationNumber("342432423").email("342432423@gmail.com").password("fdsfsd").build();
        AuthRequest authRequest2 = new AuthRequest(user2.getEmail(),user2.getPassword());

        when(userRepository.findByEmailAndPassword(user2.getEmail(), pbkdf2Encoder.encode(user2.getPassword()))).thenReturn(Mono.empty());

        //ACT
        Mono<String> returnedToken1 = userService.login(authRequest1);
        Mono<String> returnedToken2 = userService.login(authRequest2);

        //ASSERT
        returnedToken1.subscribe(subToken -> assertEquals(token, subToken));
        returnedToken2.subscribe(Assertions::assertNull);
    }


}
