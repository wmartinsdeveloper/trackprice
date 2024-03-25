package com.agrotech.usersecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agrotech.usersecurity.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from users where email = ?1 and password = ?2")
    User findByEmailAndPassword(String email, String Password);

}
