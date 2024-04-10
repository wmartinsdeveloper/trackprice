package com.agrotech.usersecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);

    }

}
