package com.team4.backend.security;

import com.team4.backend.util.JwtUtil;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMapAdapter;

import javax.sound.sampled.Port;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class SecurityContextRepositoryTest {

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    SecurityContextRepository securityContextRepository;

    @Test
    void load(){
        //ARRANGE

        MockServerHttpRequest mockServerHttpRequest = new MockServerHttpRequest(
                HttpMethod.GET,
                URI.create("/test/test"),
                "",
                HttpHeaders.AUTHORIZATION,
                new ArrayListValuedHashMap<String,String>(),
                null,
                null,
                null,
                null


        );

        //ACT

        //ASSERT
    }
}
