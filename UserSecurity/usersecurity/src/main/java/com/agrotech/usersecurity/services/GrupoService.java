package com.agrotech.usersecurity.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.repositories.GrupoRepository;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository roleRepository;

    public Optional<Usuario> findByNome(String nome) {
        return roleRepository.findByNome(nome);

    }

}
