package com.team4.backend.security;

import com.team4.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String registrationNumber = jwtUtil.getRegistrationNumberFromToken(authToken);
        //log.info(authentication.getPrincipal().toString());
        return Mono.just(!jwtUtil.isTokenExpired(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
                   // log.info(claims.toString());
                    String role = claims.get("role").toString();
                    return new UsernamePasswordAuthenticationToken(
                            registrationNumber,
                            null,
                            Arrays.asList(new SimpleGrantedAuthority(role))
                    );
                });
    }
}
