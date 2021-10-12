package com.team4.backend.security;

import org.junit.jupiter.api.Test;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OwnershipServiceTest {


    @Test
    void shouldGetLoggerUserNameForRealUser() {
        //ARRANGE
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("username");

        //ACT & ASSERT
        assertEquals("username", OwnershipService.getLoggedUserName(principal));
    }

    @Test
    void shouldNotGetLoggerUserNameNoUser() {

        //ARRANGE
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("");

        //ACT & ASSERT
        assertEquals("", OwnershipService.getLoggedUserName(principal));
    }
}
