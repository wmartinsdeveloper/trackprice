package com.agrotech.usersecurity.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    User findByEmailAndPassword(String email, String Password);

}
