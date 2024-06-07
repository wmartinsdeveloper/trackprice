package com.agrotech.usersecurity.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Grupo;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, UUID> {

    Grupo findByNome(String nome);

    boolean existsByNome(String nome);
}
