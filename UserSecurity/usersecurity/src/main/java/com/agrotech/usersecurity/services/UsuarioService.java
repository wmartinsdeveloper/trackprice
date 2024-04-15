package com.agrotech.usersecurity.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.entities.Grupo;
import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private GrupoService grupoService;

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    public UserDetails save(Usuario usuario) {

        Grupo grupo = grupoService.findByNome("ROLE_USERS");

        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);
        usuario.setEnabled(true);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setGrupo(Set.of(grupo));

        return userRepository.save(usuario);

    }

}
