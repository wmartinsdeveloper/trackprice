package com.agrotech.usersecurity.services;

import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.entities.Grupo;
import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private GrupoService grupoService;

    public UserDetails findByEmail(String email) throws UsernameNotFoundException, Exception {

        try {
            UserDetails user = userRepository.findByEmail(email);
            if (user != null) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Something went wrong, detail about that: " + e.getMessage());
        }

    }

    public List<Usuario> findAll() throws UsernameNotFoundException, Exception {

        try {
            List<Usuario> user = userRepository.findAll();
            if (user != null) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Something went wrong, detail about that: " + e.getMessage());
        }

    }

    public UserDetails save(Usuario usuario, String grupo, boolean isEnabled) {

        Grupo grupoUsr = grupoService.findByNome(grupo);

        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);
        usuario.setEnabled(isEnabled);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setGrupo(Set.of(grupoUsr));

        return userRepository.save(usuario);

    }

    public void delete(Usuario usuario) {
        userRepository.delete(usuario);
    }

    public void update(Usuario savedUsuario) {
        System.out.println(savedUsuario);
        userRepository.save(savedUsuario);
    }

}
