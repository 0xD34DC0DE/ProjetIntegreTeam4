package com.team4.backend.configuration;

import com.team4.backend.security.AuthenticationManager;
import com.team4.backend.security.SecurityContextRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecurityContextConfigurationTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    SecurityContextRepository securityContextRepository;

    @InjectMocks
    SecurityConfiguration securityConfiguration;

    @Test
    void securityWebFilterChain(){
        //ARRANGE

        //ACT

        //ASSERT

    }
}
