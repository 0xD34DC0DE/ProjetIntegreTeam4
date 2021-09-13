package com.team4.backend.service;

import com.team4.backend.model.User;
import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.model.dto.AuthResponse;
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
    void login(){
        //ARRANGE
        User user = new User();
        String token = "adasdadadsas";
        AuthRequest authRequest1 = new AuthRequest("123123","sdadasd");

        when(pbkdf2Encoder.encode(authRequest1.getPassword())).thenReturn("d893szwerf=");
        when(userRepository.findByRegistrationNumberAndPassword(authRequest1.getRegistrationNumber(),pbkdf2Encoder.encode(authRequest1.getPassword()))).thenReturn(Mono.just(user));
        when(jwtUtil.generateToken(user)).thenReturn(token);

        AuthRequest authRequest2 = new AuthRequest("3123123","dfsadasad");

        when(pbkdf2Encoder.encode(authRequest2.getPassword())).thenReturn("-0-adkc7ayqe=");
        when(userRepository.findByRegistrationNumberAndPassword(authRequest2.getRegistrationNumber(),pbkdf2Encoder.encode(authRequest2.getPassword()))).thenReturn(Mono.empty());

        //ACT

        Mono<AuthResponse> returnedToken1 = userService.login(authRequest1);
        Mono<AuthResponse> returnedToken2 = userService.login(authRequest2);

        //ASSERT
        returnedToken1.subscribe(Assertions::assertNotNull);
        returnedToken2.subscribe(Assertions::assertNull);
    }


}
