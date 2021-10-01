package com.team4.backend.configuration;

import com.team4.backend.controller.UserController;
import com.team4.backend.model.ExamplePerson;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import com.team4.backend.security.AuthenticationManager;
import com.team4.backend.security.SecurityContextRepository;
import com.team4.backend.testdata.SecurityMockData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Log
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = {SecurityConfiguration.class})
public class SecurityContextConfigurationTest {


    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    SecurityContextRepository securityContextRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void securityWebFilterChainWithInvalidToken() {
        //ARRANGE
        String token1 = "invalidToken";
        Authentication authentication = SecurityMockData.createAuthentication("");

        when(authenticationManager.authenticate(any())).thenReturn(Mono.just(authentication));
        when(securityContextRepository.load(any())).thenReturn(Mono.just(new SecurityContextImpl(authentication)));

        //ACT
        HttpStatus httpStatus1 = webTestClient.get().uri("/non_existent")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1)
                .exchange().returnResult(User.class).getStatus();

        //ASSERT
        assertEquals(HttpStatus.FORBIDDEN, httpStatus1);
    }

    @Test
    void securityWebFilterChainWithNoAuthorizationHeader() {
        //ARRANGE
        when(authenticationManager.authenticate(any())).thenReturn(Mono.empty());
        when(securityContextRepository.load(any())).thenReturn(Mono.empty());

        //ACT
        HttpStatus httpStatus1 = webTestClient.get().uri("/non_existent")
                .exchange().returnResult(User.class).getStatus();

        //ASSERT
        assertEquals(HttpStatus.UNAUTHORIZED, httpStatus1);
    }
}
