package com.team4.backend.security;

import java.security.Principal;

public abstract class OwnershipService {

    public static String getLoggerUserEmail(Principal principal) {
        return principal != null ? principal.getName() : "";
    }
}
