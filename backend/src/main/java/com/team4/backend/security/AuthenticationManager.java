package com.team4.backend.security;

import com.team4.backend.util.JwtUtil;
import lombok.extern.java.Log;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Log
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    public AuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        return Mono.justOrEmpty(jwtUtil.isTokenValid(authToken) && !jwtUtil.isTokenExpired(authToken))
                .map(isValid -> isValid ?
                        new UsernamePasswordAuthenticationToken(
                                jwtUtil.getEmailFromToken(authToken),
                                null,
                                Arrays.asList(new SimpleGrantedAuthority(jwtUtil.getAllClaimsFromToken(authToken).get("role").toString())))
                        : new UsernamePasswordAuthenticationToken("", ""));
    }
}
