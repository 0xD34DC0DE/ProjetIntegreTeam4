package com.team4.backend.security;

import com.team4.backend.model.enums.Role;
import com.team4.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;


import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AuthenticationManagerTest {

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    AuthenticationManager authenticationManager;


    @Test
    void authenticate(){
        //ARRANGE
        String token1 = "dsaouaau3r0quea-2342";
        String email1 ="123456789@gmail.com";

        when(jwtUtil.isTokenValid(token1) && !jwtUtil.isTokenExpired(token1)).thenReturn(true);
        when(jwtUtil.getEmailFromToken(token1)).thenReturn(email1);
        when(jwtUtil.getAllClaimsFromToken(token1)).thenReturn(new DefaultClaims(Collections.singletonMap("role",Role.STUDENT)));

        String token2 = "sdio89afoishajasl";

        when(jwtUtil.isTokenValid(token2) && !jwtUtil.isTokenExpired(token2)).thenReturn(false);

        //ACT
        Mono<Authentication> authentication1 = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token1,token1));
        Mono<Authentication> authentication2 = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token2,token2));

        //ASSERT
        authentication1.subscribe(auth -> assertEquals(email1,auth.getPrincipal()));
        authentication2.subscribe(auth -> assertTrue(auth.getPrincipal().toString().isEmpty()));
    }
}
