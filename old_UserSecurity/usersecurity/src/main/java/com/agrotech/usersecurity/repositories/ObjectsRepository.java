package com.agrotech.usersecurity.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agrotech.usersecurity.entities.Objects;

@Repository
public interface ObjectsRepository extends CrudRepository<Objects, UUID> {

}
