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

    /*
     * Authenticates a user and returns a response indicating whether the
     * authentication was successful.
     * 
     * @param authentication the authentication object containing the user's
     * credentials
     * 
     * @return a ResponseEntity with a status code indicating the outcome of the
     * authentication attempt
     * 
     * Example:
     * <pre>
     * {@code
     * Authentication authentication = new Authentication("username", "password");
     * ResponseEntity<String> response = authenticate(authentication);
     * if (response.getStatusCode() == HttpStatus.OK) {
     * System.out.println("Authentication successful!");
     * } else {
     * System.out.println("Authentication failed!");
     * }
     * }
     * </pre>
     */

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
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("<h1>Error to save the user ! </h1></br>" + e.getMessage());
        }

    }

    /*
     * Registers a new user and returns a response indicating whether the
     * registration was successful.
     * 
     * @param usuario the user object containing the registration information
     * 
     * @return a ResponseEntity with a status code indicating the outcome of the
     * registration attempt
     * 
     * Example:
     * <pre>
     * {@code
     * Usuario usuario = new Usuario("username", "email@example.com", "password");
     * ResponseEntity<String> response = registerUser(usuario);
     * if (response.getStatusCode() == HttpStatus.CREATED) {
     * System.out.println("User registered successfully!");
     * } else {
     * System.out.println("Registration failed!");
     * }
     * }
     * </pre>
     */

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Usuario usuario) {

        Usuario savedUsuario = null;
        ResponseEntity response = null;

        try {

            if (usuarioService.findByEmail(usuario.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already exists. Please, choose another one and try again !");
            } else {
                savedUsuario = (Usuario) usuarioService.save(usuario, "USERS", false);

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

   

}
