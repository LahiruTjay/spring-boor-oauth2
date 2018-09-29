package com.example.auth;

import org.springframework.security.core.AuthenticationException;

public class RoleNotFoundException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public RoleNotFoundException() {
        super("RoleNotFoundException");
    }
}
