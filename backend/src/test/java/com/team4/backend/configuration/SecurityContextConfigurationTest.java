package com.team4.backend.configuration;

import com.team4.backend.controller.UserController;
import com.team4.backend.model.ExamplePerson;
import com.team4.backend.model.enums.Role;
import com.team4.backend.security.AuthenticationManager;
import com.team4.backend.security.SecurityContextRepository;
import com.team4.backend.testdata.SecurityMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = SecurityConfiguration.class)
public class SecurityContextConfigurationTest {


    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    SecurityContextRepository securityContextRepository;


    @Autowired
    WebTestClient webTestClient;

    @Test
    void securityWebFilterChain(){
        //ARRANGE
        Authentication authentication = SecurityMockData.createAuthentication("");

        when(securityContextRepository.load(any())).thenReturn(Mono.just(new SecurityContextImpl(authentication)));

        //ACT

        HttpStatus httpStatus = webTestClient.get().uri("/person/personByNameFirstLetter/W")
                .header(HttpHeaders.AUTHORIZATION,"Bearer adsadasdsad")
                .exchange().returnResult(ExamplePerson.class).getStatus();



        //ASSERT
        assertEquals(HttpStatus.FORBIDDEN,httpStatus);
    }
}
