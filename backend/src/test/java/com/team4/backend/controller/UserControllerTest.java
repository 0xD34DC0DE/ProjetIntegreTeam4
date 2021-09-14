package com.team4.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.backend.configuration.SecurityConfiguration;
import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.model.dto.AuthResponse;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.security.AuthenticationManager;
import com.team4.backend.service.UserService;
import com.team4.backend.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = UserController.class,excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;


    @Test
    public void login(){
        //ARRANGE
        AuthRequest authRequest = new AuthRequest("12345678","massou123");

        when(userService.login(authRequest)).thenReturn(Mono.just(new AuthResponse("s23esadsadasa")));

        //ACT
        HttpStatus httpStatus= webTestClient
                .post().uri("/user/login")
                .bodyValue(authRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AuthResponse.class).returnResult().getStatus();


        //ASSERT
        assertEquals(HttpStatus.OK,httpStatus);
    }



}
