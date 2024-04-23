package com.agrotech.usersecurity.config;

import java.util.List;
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
        Grupo role1 = new Grupo("ADMIN");
        Grupo role2 = new Grupo("USERS");

        Usuario admin = new Usuario("administrator", "admin@gmail.com", "admin", passwordEncoder.encode("Manager1"));
        admin.setGrupo(Set.of(role1));
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);

        roleRepo.save(role1);
        roleRepo.save(role2);
        userRepo.save(admin);

    }

}