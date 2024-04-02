package com.agrotech.usersecurity.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);

}
