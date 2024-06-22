package com.agrotech.usersecurity.controller;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.services.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ManagerUsersController {

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    private UsuarioService usuarioService;

    public ManagerUsersController() {
    }

    @PostMapping("/admin/user/register")
    public ResponseEntity registerAdminUser(@RequestBody Usuario usuario) {

        Usuario savedUsuario = null;
        ResponseEntity response = null;

        try {

            if (usuarioService.findByEmail(usuario.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already exists. Please, choose another one and try again !");
            } else {
                savedUsuario = (Usuario) usuarioService.save(usuario, "ADMIN", true);

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

    @GetMapping("/admin/users")
    public ResponseEntity listAllAdminUser() {

        try {
            List<Usuario> users = usuarioService.findAll();
            if (users != null)
                return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(users);
            else {
                return ResponseEntity.status(HttpStatus.OK)
                        .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                        .body("Users not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Error message: " + e.getMessage());
        }

    }

    @GetMapping("/admin/user/{email}")
    public ResponseEntity listAdminUserByEmail(@PathVariable("email") String email) {

        try {
            UserDetails user = usuarioService.findByEmail(email);
            if (user != null) {
                return ResponseEntity.ok().body(user);
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                        .body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Error message: " + e.getMessage());
        }

    }

    @DeleteMapping("/admin/user/delete/{email}")
    public ResponseEntity deleteAdminUser(@PathVariable("email") String email) {

        try {
            Usuario user = (Usuario) usuarioService.findByEmail(email);
            if (user != null) {
                usuarioService.delete(user);
                return ResponseEntity.ok().body("User was removed.");
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Error message: " + e.getMessage());
        }

    }

    @PutMapping("/admin/user/update")
    public ResponseEntity updateAdminUser(@RequestBody Usuario usuario) {

        Usuario savedUsuario = null;
        ResponseEntity response = null;

        try {
            savedUsuario = (Usuario) usuarioService.findByEmail(usuario.getEmail());
            if (savedUsuario != null) {
                usuarioService.update(usuario);
                response = ResponseEntity.status(HttpStatus.OK)
                        .body("User Updated.");
            } else {
                response = ResponseEntity.status(HttpStatus.CREATED).body("User not found.");
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error to update user." + e.getMessage());
        }

        return response;

    }

}
