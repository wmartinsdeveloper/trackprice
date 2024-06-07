package com.agrotech.usersecurity.controller;

import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.services.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class AuthenticationController {

        @Autowired
        private AuthenticationService authenticationService;

        @Autowired
        HttpServletResponse httpServletResponse;

        @PostMapping("/login")
        public ResponseEntity<Void> authenticate(Authentication authentication) {

                try {
                        if (authentication.isAuthenticated()) {
                                httpServletResponse.setHeader("Authorization",
                                                authenticationService.authenticate(authentication));
                                return ResponseEntity.ok().build();
                        } else {
                                return ResponseEntity.notFound().build();
                        }
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                        return ResponseEntity.notFound().build();
                }

        }

}
