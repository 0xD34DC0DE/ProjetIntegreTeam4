package com.team4.backend.configuration;

import com.team4.backend.security.AuthenticationManager;
import com.team4.backend.security.SecurityContextRepository;
import com.team4.backend.testdata.SecurityMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class SecurityContextConfigurationTest {

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    SecurityContextRepository securityContextRepository;

    @InjectMocks
    SecurityConfiguration securityConfiguration;


    @Test
    void securityWebFilterChain(){
        //ARRANGE
        Authentication authentication = SecurityMockData.createAuthentication("34ds2131398asdh23");
        ServerHttpSecurity serverHttpSecurity = ServerHttpSecurity.http().authenticationManager(authenticationManager);

        when(authenticationManager.authenticate(authentication)).thenReturn(Mono.just(authentication));

        //ACT
        SecurityWebFilterChain securityWebFilterChain = securityConfiguration.securityWebFilterChain(serverHttpSecurity);

        //ASSERT

    }
}
