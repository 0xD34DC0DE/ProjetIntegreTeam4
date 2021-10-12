package com.team4.backend.security;

import java.security.Principal;

public abstract class OwnershipService {

    public static String getLoggedUserName(Principal principal) {
        return principal.getName();
    }
}
