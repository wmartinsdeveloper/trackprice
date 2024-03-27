package com.agrotech.usersecurity.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Privilege;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

}