package com.agrotech.usersecurity.repositories;

import org.springframework.data.repository.CrudRepository;

import com.agrotech.usersecurity.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
