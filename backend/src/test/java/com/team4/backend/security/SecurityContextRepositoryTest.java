package com.team4.backend.security;

import com.team4.backend.model.enums.Role;
import com.team4.backend.testdata.SecurityMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityContextRepositoryTest {

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    SecurityContextRepository securityContextRepository;

    @Test
    void save(){
        //ASSERT
        assertThrows(UnsupportedOperationException.class,()->securityContextRepository.save(null,null));
    }


    @Test
    void load(){
        //ARRANGE
        //First mock request with a valid token, should be able to get principal
        String token1 = "ds8a8dha89y8dha88df98asfa";
        Authentication authentication1 = SecurityMockData.createAuthentication("123456789@gmail.com",null,Arrays.asList(new SimpleGrantedAuthority(Role.STUDENT.toString())));

        when(authenticationManager.authenticate(SecurityMockData.createAuthentication(token1))).thenReturn(Mono.just(authentication1));

        //Second mock request with invalid token/empty headers, should not initialize a security context
        String token2 = "324fdw345rewq23432123123";
        Authentication authentication2 = SecurityMockData.createAuthentication("");
        when(authenticationManager.authenticate(SecurityMockData.createAuthentication(token2))).thenReturn(Mono.just(authentication2));

        //ACT
        Mono<SecurityContext> securityContextMono1 = securityContextRepository.load(SecurityMockData.createMockWebExchange("/test1/test1",token1));
        Mono<SecurityContext> securityContextMono2 = securityContextRepository.load(SecurityMockData.createMockWebExchange("/test2/test2",token2));

        //ASSERT
        securityContextMono1.subscribe(securityContext -> assertFalse(securityContext.getAuthentication().getPrincipal().toString().isEmpty()));
        securityContextMono2.subscribe(securityContext -> assertTrue(securityContext.getAuthentication().getPrincipal().toString().isEmpty()));
    }
}
