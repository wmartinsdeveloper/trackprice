package com.agrotech.usersecurity.controller;

import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.entities.User;
import com.agrotech.usersecurity.repositories.UserRepository;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/Usersecurity")
public class Usersecurity {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login/{email}{password}")
    public ResponseEntity<User> findById(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return ResponseEntity.ok(user);

    }

}
