package com.agrotech.usersecurity.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
