package com.agrotech.usersecurity.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Grupo;
import com.agrotech.usersecurity.entities.Usuario;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, UUID> {

    Optional<Usuario> findByNome(String nome);

}
