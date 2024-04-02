package com.agrotech.usersecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.services.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class AuthenticationControler {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public String authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);

    }

}
