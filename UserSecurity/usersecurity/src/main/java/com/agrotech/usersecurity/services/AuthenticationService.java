package com.agrotech.usersecurity.services;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private TokenService tokenService;

    public AuthenticationService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String authenticate(Authentication authentication) throws Exception {

        if (authentication.isAuthenticated()) {
            return tokenService.generateToken(authentication);
        } else {
            throw new Exception("User not authenticated");
        }

    }

}
