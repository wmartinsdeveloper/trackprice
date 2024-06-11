package com.agrotech.usersecurity.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agrotech.usersecurity.entities.Grupo;
import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.repositories.GrupoRepository;
import com.agrotech.usersecurity.repositories.UsuarioRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UsuarioRepository userRepo;

    @Autowired
    GrupoRepository grupoRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {

        /*
         * Creation of default user groups and admin user
         * This method will be executed every time the application starts
         */

        // Create registers on database
        Grupo grupo1 = new Grupo("ADMIN");
        Grupo grupo2 = new Grupo("USERS");

        if (!grupoRepo.existsByNome(grupo1.getNome())) {
            grupoRepo.save(grupo1);
        }

        if (!grupoRepo.existsByNome(grupo2.getNome())) {
            grupoRepo.save(grupo2);
        }

        Usuario admin = new Usuario("admin@gmail.com", "admin", passwordEncoder.encode("Manager1"));
        admin.setGrupo(Set.of(grupo1));
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);

        if (!userRepo.existsByEmail(admin.getEmail())) {
            userRepo.save(admin);
        }

    }

}