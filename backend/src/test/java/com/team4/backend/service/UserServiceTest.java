package com.team4.backend.service;

import com.team4.backend.dto.AuthRequestDto;
import com.team4.backend.model.User;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.JwtUtil;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        User user1 = User.builder().registrationNumber("123456789").email("123456789@gmail.com").password("p@22w0rd").build();
        AuthRequestDto authRequestDto1 = new AuthRequestDto(user1.getEmail(), user1.getPassword());
        String token = "123456789";

        when(userRepository.findByEmailAndPasswordAndIsEnabledTrue(user1.getEmail(), pbkdf2Encoder.encode(user1.getPassword()))).thenReturn(Mono.just(user1));
        when(jwtUtil.generateToken(user1)).thenReturn("123456789");


        User user2 = User.builder().registrationNumber("342432423").email("342432423@gmail.com").password("p@ssw0rd").build();
        AuthRequestDto authRequestDto2 = new AuthRequestDto(user2.getEmail(), user2.getPassword());

        when(userRepository.findByEmailAndPasswordAndIsEnabledTrue(user2.getEmail(), pbkdf2Encoder.encode(user2.getPassword()))).thenReturn(Mono.empty());

        //ACT
        Mono<String> returnedToken1 = userService.login(authRequestDto1);
        Mono<String> returnedToken2 = userService.login(authRequestDto2);

        //ASSERT
        StepVerifier.create(returnedToken1)
                .consumeNextWith(t -> assertEquals(token, t))
                .verifyComplete();

        StepVerifier.create(returnedToken2).verifyError(ResponseStatusException.class);
    }

    @Test
    void isEmailTaken() {
        // ARRANGE
        String email1 = "123456789@gmail.com";
        String email2 = "test@gmail.com";

        when(userRepository.existsByEmail(email1)).thenReturn(Mono.just(true));
        when(userRepository.existsByEmail(email2)).thenReturn(Mono.just(false));

        // ACT
        Mono<Boolean> booleanMono1 = userService.isEmailTaken(email1);
        Mono<Boolean> booleanMono2 = userService.isEmailTaken(email2);

        // ASSERT
        StepVerifier.create(booleanMono1)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();

        StepVerifier.create(booleanMono2)
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
    }
}
