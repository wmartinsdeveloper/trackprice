package com.agrotech.usersecurity.resources;

import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.entities.User;
import com.agrotech.usersecurity.services.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/Usersecurity")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Optional<User>> Login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.Login(email);
        return ResponseEntity.ok(user);

    }

}
