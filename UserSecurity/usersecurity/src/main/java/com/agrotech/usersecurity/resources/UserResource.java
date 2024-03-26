package com.agrotech.usersecurity.resources;

import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.entities.User;
import com.agrotech.usersecurity.services.UserService;

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
    public ResponseEntity<User> signIn(@RequestParam String email, @RequestParam String password) {
        User user = userService.signIn(email, password);
        return ResponseEntity.ok(user);

    }

}
