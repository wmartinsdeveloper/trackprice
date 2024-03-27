package com.agrotech.usersecurity.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agrotech.usersecurity.entities.Objects;
import com.agrotech.usersecurity.entities.Privilege;
import com.agrotech.usersecurity.entities.Role;
import com.agrotech.usersecurity.entities.User;
import com.agrotech.usersecurity.entities.enums.UserStatus;
import com.agrotech.usersecurity.repositories.ObjectsRepository;
import com.agrotech.usersecurity.repositories.PrivilegeRepository;
import com.agrotech.usersecurity.repositories.RoleRepository;
import com.agrotech.usersecurity.repositories.UserRepository;

import jakarta.transaction.Transactional;

/**
 * DataInitializer
 */

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepo;

    @Autowired
    ObjectsRepository objectsRepo;

    @Autowired
    PrivilegeRepository privilegeRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        // Delete All registries on database
        userRepo.deleteAll();
        roleRepo.deleteAll();
        objectsRepo.deleteAll();
        privilegeRepo.deleteAll();

        // Create registers on database
        // Objects objects = new Objects("Login");
        // Privilege privilege = new Privilege("Read");
        // Role role = new Role("Administrator", Set.of(privilege), Set.of(objects));

        // objectsRepo.save(objects);
        // privilegeRepo.save(privilege);
        // roleRepo.save(role);
        // userRepo.save(
        // new User("Wellington", "wfmzipi@gmail.com", "wfmzipi",
        // passwordEncoder.encode("Manager1"),
        // UserStatus.TRUE, Set.of(role)));

    }

}