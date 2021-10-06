package com.team4.backend.controller;

import com.team4.backend.service.SupervisorService;
import com.team4.backend.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = SupervisorController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class SupervisorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    SupervisorService supervisorService;

    @MockBean
    UserService userService;

}