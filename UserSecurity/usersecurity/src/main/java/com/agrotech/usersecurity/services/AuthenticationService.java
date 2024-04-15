package com.agrotech.usersecurity.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private TokenService tokenService;

    public AuthenticationService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String authenticate(Authentication authentication) {
        // System.out.println("Gerando toke ...............");
        return tokenService.generateToken(authentication);
    }
}
