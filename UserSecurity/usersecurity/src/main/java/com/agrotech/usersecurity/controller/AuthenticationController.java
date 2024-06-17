package com.agrotech.usersecurity.controller;

import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.services.AuthenticationProviderService;
import com.agrotech.usersecurity.services.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthenticationController {

        @Autowired
        private AuthenticationProviderService authenticationService;

        @Autowired
        HttpServletResponse httpServletResponse;

        @Autowired
        private UsuarioService usuarioService;

        @PostMapping("/login")
        public ResponseEntity<String> authenticate(Authentication authentication) {
                try {
                       if (authentication.isAuthenticated()) {
                            return ResponseEntity.ok().build();
                        } else {
                           return ResponseEntity.notFound().build();
                        }
                } catch (Exception e) {
                        // e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.CREATED).body("<h1>Error to save the user ! </h1></br>" + e.getMessage());
                }

        }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Usuario usuario) {

        Usuario savedUsuario = null;
        ResponseEntity response = null;

        try {

            if (usuarioService.findByEmail(usuario.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already exists. Please, choose another one and try again !");
            } else {
                savedUsuario = (Usuario) usuarioService.save(usuario);

                if (savedUsuario.getId() != null) {
                    response = ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
                }
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<h1>Error to save the user ! </h1></br>" + e.getMessage());
        }

        return response;

    }


    @GetMapping("/admin")
    public ResponseEntity getMethodName() {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body("Private Area");
    }



}
