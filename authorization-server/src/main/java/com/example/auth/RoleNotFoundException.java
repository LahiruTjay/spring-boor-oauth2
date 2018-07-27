package com.example.auth;

import org.springframework.security.core.AuthenticationException;

public class RoleNotFoundException extends AuthenticationException{

    public RoleNotFoundException() {
        super("RoleNotFoundException");
    }
}
