package com.agrotech.usersecurity.repositories;

import org.springframework.data.repository.CrudRepository;

import com.agrotech.usersecurity.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
