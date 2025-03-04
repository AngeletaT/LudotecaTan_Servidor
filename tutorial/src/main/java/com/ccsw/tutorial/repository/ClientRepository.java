package com.ccsw.tutorial.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.entities.Client;

/**
 * Repository interface for Client entity.
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findByName(String name);
}
