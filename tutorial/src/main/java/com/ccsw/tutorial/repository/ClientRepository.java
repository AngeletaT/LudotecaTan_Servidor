package com.ccsw.tutorial.repository;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.entities.Client;

/**
 * @author ccsw
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
