package com.agrotech.usersecurity.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

}
