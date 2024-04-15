package com.agrotech.usersecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.entities.Grupo;
import com.agrotech.usersecurity.repositories.GrupoRepository;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo findByNome(String nome) {
        return grupoRepository.findByNome(nome);

    }

}
