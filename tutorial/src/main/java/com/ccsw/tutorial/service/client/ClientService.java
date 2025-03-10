package com.ccsw.tutorial.service.client;

import java.util.List;

import com.ccsw.tutorial.dto.client.ClientDto;
import com.ccsw.tutorial.entities.Client;
import com.ccsw.tutorial.exception.ClientAlreadyExistsException;

/**
 * @author ccsw
 *
 */
public interface ClientService {

    /**
     * Recupera una {@link Client} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Client}
     */
    Client get(Long id);

    /**
     * Método para recuperar todas las {@link Client}
     *
     * @return {@link List} de {@link Client}
     */
    List<Client> findAll();

    /**
     * Método para crear o actualizar una {@link Client}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, ClientDto dto) throws ClientAlreadyExistsException;

    /**
     * Método para borrar una {@link Client}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;
}