package com.agrotech.usersecurity.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findAll();

    void deleteByEmail(String email);

    void save(Optional<Usuario> savedUsuario);

}
