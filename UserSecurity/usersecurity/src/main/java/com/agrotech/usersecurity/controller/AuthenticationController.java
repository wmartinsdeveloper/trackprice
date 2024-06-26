package com.agrotech.usersecurity.controller;

import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.dto.dtoUsuario;
import com.agrotech.usersecurity.services.AuthenticationProviderService;
import com.agrotech.usersecurity.services.MailService;
import com.agrotech.usersecurity.services.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationProviderService authenticationService;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    MailService emailSender;


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
    public ResponseEntity<String> registerUser(@RequestBody dtoUsuario usuario) {

        ResponseEntity response = null;

        try {

             if (usuario != null) {
                usuarioService.novoUsuario(usuario);
                response = ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

            } else {                
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Json body is empity, fill up the content of json and try again !");
                
                
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<h1>Error to save the user ! </h1></br>" + e.getMessage());
        }

        return response;

    }

     
    @RequestMapping("/login/active")
    public ResponseEntity<String> activeUser(@RequestParam("email") String email,@RequestParam("uuid") String uuid) {

        ResponseEntity response = null;
       
        try {
            
            if (email != null && uuid != null) {
                usuarioService.activeUser(email, uuid);  
                response =  ResponseEntity.status(HttpStatus.OK).body("User actived successfully");

            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
  
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<h1>Error to active user ! </h1></br>" + e.getMessage());
        }
    
        return response;

    }


    @PutMapping("/login/user/changepassword")
    public ResponseEntity changePasswordUser(@RequestBody String password, @RequestParam("email") String email) {

        ResponseEntity response = null;

        try {
            if (password != null && email != null) {
                usuarioService.changePasswordUser(email, password);
                response = ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
                
            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error to update user:<br>" + e.getMessage());
        }

        return response;

    }





}
