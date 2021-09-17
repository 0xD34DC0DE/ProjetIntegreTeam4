package com.team4.backend.security;

import com.team4.backend.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecurityContextRepositoryTest {

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    SecurityContextRepository securityContextRepository;

    @Test
    void load(){
        //ARRANGE
        /*
        MultiValueMap<String,String> multiValueMap =
        MockServerWebExchange mockServerWebExchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test/test")
                .headers()
        );

        MockServerHttpRequest mockServerHttpRequest = new MockServerHttpRequest(
                HttpMethod.GET,
                URI.create("/test/test"),
                "",
                HttpHeaders.AUTHORIZATION,
                new ArrayListValuedHashMap<String,String>(),
                null,
                null,
                null,
                dataBufferFlux
        );
         */


        //ACT

        //ASSERT
    }
}
