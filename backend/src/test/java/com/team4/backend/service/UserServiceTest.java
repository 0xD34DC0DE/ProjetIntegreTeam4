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
import static org.mockito.ArgumentMatchers.any;
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
    void loginSuccess() {
        //ARRANGE
        User user = User.builder().registrationNumber("123456789").email("123456789@gmail.com").password("p@22w0rd").build();
        AuthRequestDto authRequestDto = new AuthRequestDto(user.getEmail(), user.getPassword());
        String token = "123456789";

        when(userRepository.findByEmailAndPasswordAndIsEnabledTrue(user.getEmail(),
                pbkdf2Encoder.encode(user.getPassword()))).thenReturn(Mono.just(user));

        when(jwtUtil.generateToken(user)).thenReturn("123456789");

        //ACT
        Mono<String> tokenMono = userService.login(authRequestDto);

        //ASSERT
        StepVerifier.create(tokenMono)
                .consumeNextWith(t -> assertEquals(token, t))
                .verifyComplete();
    }

    @Test
    void loginNoSuccess() {
        //ARRANGE
        User user = User.builder()
                .registrationNumber("342432423")
                .email("342432423@gmail.com")
                .password("p@ssw0rd")
                .build();

        AuthRequestDto authRequestDto = new AuthRequestDto(user.getEmail(), user.getPassword());

        when(userRepository.findByEmailAndPasswordAndIsEnabledTrue(user.getEmail(),
                pbkdf2Encoder.encode(user.getPassword()))).thenReturn(Mono.empty());

        //ACT
        Mono<String> tokenMono = userService.login(authRequestDto);

        //ASSERT
        StepVerifier.create(tokenMono).verifyError(ResponseStatusException.class);
    }

    @Test
    void emailTaken() {
        //ARRANGE
        String email = "123456789@gmail.com";

        when(userRepository.existsByEmail(email)).thenReturn(Mono.just(true));

        //ACT
        Mono<Boolean> existsMono = userService.existsByEmail(email);

        //ASSERT
        StepVerifier.create(existsMono)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void emailNotTaken() {
        //ARRANGE
        String email = "test@gmail.com";

        when(userRepository.existsByEmail(any(String.class))).thenReturn(Mono.just(false));

        //ACT
        Mono<Boolean> existsMono = userService.existsByEmail(email);

        //ASSERT
        StepVerifier.create(existsMono)
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
    }
}
