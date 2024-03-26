package com.agrotech.usersecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.entities.User;
import com.agrotech.usersecurity.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signIn(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);

    }

}
