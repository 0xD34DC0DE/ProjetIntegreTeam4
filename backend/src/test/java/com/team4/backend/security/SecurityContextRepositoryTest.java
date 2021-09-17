package com.team4.backend.security;

import com.team4.backend.model.enums.Role;
import com.team4.backend.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
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
        Authentication authentication1 = new UsernamePasswordAuthenticationToken("123456789@gmail.com",null, Arrays.asList(new SimpleGrantedAuthority(Role.STUDENT.toString())));
        MultiValueMap<String,String> headers1 = new LinkedMultiValueMap<>();

        headers1.add(HttpHeaders.AUTHORIZATION,"Bearer ds8a8dha89y8dha88df98asfa" + token1);

        MockServerWebExchange mockServerWebExchange1 = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test1/test1")
                .headers(headers1)
        );

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token1,token1))).thenReturn(Mono.just(authentication1));

        //Second mock request with invalid token, should not initialize a security context
        String token2 = "ds8a8dha89y8dha88df98asfa";
        MultiValueMap<String,String> headers2 = new LinkedMultiValueMap<>();

        headers2.add(HttpHeaders.AUTHORIZATION,"Bearer " + token2);

        MockServerWebExchange mockServerWebExchange2 = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test2/tes2t")
                        .headers(headers2)
        );

        //ACT
        Mono<SecurityContext> securityContextMono1 = securityContextRepository.load(mockServerWebExchange1);
        Mono<SecurityContext> securityContextMono2 = securityContextRepository.load(mockServerWebExchange2);

        //ASSERT
        securityContextMono1.subscribe(securityContext -> assertFalse(securityContext.getAuthentication().getPrincipal().toString().isEmpty()));
        securityContextMono2.subscribe(Assertions::assertNotNull);
    }
}
