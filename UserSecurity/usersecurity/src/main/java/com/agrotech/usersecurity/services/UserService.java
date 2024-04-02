package com.agrotech.usersecurity.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.entities.Users;
import com.agrotech.usersecurity.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);

    }

}
