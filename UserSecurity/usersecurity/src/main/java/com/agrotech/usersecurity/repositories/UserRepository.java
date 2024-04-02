package com.agrotech.usersecurity.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByNome(String username);

}
