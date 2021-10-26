package com.team4.backend.security;

import java.security.Principal;

public abstract class UserSessionService {

    public static String getLoggedUserEmail(Principal principal) {
        return principal != null ? principal.getName() : "";
    }

}
