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
    GrupoRepository roleRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {

        // Delete All registries on database
        userRepo.deleteAll();
        roleRepo.deleteAll();

        // Create registers on database
        Grupo role1 = new Grupo("ROLE_ADMIN");
        Grupo role2 = new Grupo("ROLE_USERS");

        roleRepo.save(role1);
        roleRepo.save(role2);
        // userRepo.save(
        // new Usuario("Wellington", "wfmzipi@gmail.com", "wfmzipi",
        // passwordEncoder.encode("Manager1"), true, true, true, true,
        // Set.of(role1, role2)));

    }

}