package com.team4.backend.controller;

import com.team4.backend.dto.AuthRequestDto;
import com.team4.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = UserController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    @Test
    public void login(){
        //ARRANGE
        AuthRequestDto authRequestDto = new AuthRequestDto("444555@gmail.com", "massou123");

        when(userService.login(authRequestDto)).thenReturn(Mono.just("sadasdadsas"));

        //ACT
        HttpStatus httpStatus= webTestClient
                .post()
                .uri("/user/login")
                .bodyValue(authRequestDto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class).returnResult().getStatus();

        //ASSERT
        assertEquals(HttpStatus.OK,httpStatus);
    }

}
