package com.team4.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.model.dto.AuthResponse;
import com.team4.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = UserController.class)
public class UserControllerTest {


    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void login(){
        //ARRANGE
        AuthRequest authRequest = new AuthRequest("12345678","massou123");

        when(userService.login(authRequest)).thenReturn(Mono.just(new AuthResponse()));



        //ACT


        //ASSERT
    }



}
