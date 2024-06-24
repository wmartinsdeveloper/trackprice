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
        Grupo grupo1 = grupoRepo.findByNome("ADMIN");
        Grupo grupo2 = grupoRepo.findByNome("USERS");

        if (grupo1 == null) {
            grupo1= new Grupo("ADMIN");
            grupoRepo.save(grupo1);
        }

        if (grupo2 == null) {
            grupo2= new Grupo("USERS");
            grupoRepo.save(grupo2);
        }

        Usuario admin = new Usuario("admin@gmail.com", "admin", passwordEncoder.encode("$4gr0t3ch$"));
        admin.setGrupo(Set.of(grupo1, grupo2));
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);

        if (!userRepo.existsByEmail(admin.getEmail())) {
            userRepo.save(admin);
        }

    }

}