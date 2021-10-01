package com.team4.backend.testdata;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

public abstract class SecurityMockData {

    public static UsernamePasswordAuthenticationToken createAuthentication(String principal) {
        return new UsernamePasswordAuthenticationToken(principal, principal);
    }

    public static UsernamePasswordAuthenticationToken createAuthentication(String principal, String credentials, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        return new UsernamePasswordAuthenticationToken(principal, credentials, simpleGrantedAuthorities);
    }

    public static MockServerWebExchange createMockWebExchange(String url, String token) {
        MultiValueMap<String, String> headers1 = new LinkedMultiValueMap<>();

        headers1.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return MockServerWebExchange.from(
                MockServerHttpRequest.get(url)
                        .headers(headers1)
        );
    }

}
