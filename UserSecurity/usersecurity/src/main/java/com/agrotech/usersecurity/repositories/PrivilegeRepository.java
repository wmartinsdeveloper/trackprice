package com.agrotech.usersecurity.repositories;

import org.springframework.data.repository.CrudRepository;

import com.agrotech.usersecurity.entities.Privilege;

public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

}