package com.ccsw.tutorial.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.dto.client.ClientDto;
import com.ccsw.tutorial.entities.Client;
import com.ccsw.tutorial.exceptions.client.ClientNotFoundException;
import com.ccsw.tutorial.exceptions.client.ClientAlreadyExistsException;
import com.ccsw.tutorial.exceptions.client.InvalidClientException;
import com.ccsw.tutorial.service.client.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ccsw
 * 
 */
@Tag(name = "Client", description = "API of Client")
@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todas las {@link Client}
     *
     * @return {@link List} de {@link ClientDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Clients")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ClientDto> findAll() {
        List<Client> clients = this.clientService.findAll();

        return clients.stream().map(e -> mapper.map(e, ClientDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una {@link Client}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Client")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public ResponseEntity<String> save(@PathVariable(name = "id", required = false) Long id,
            @RequestBody ClientDto dto) {
        try {
            this.clientService.save(id, dto);
            return ResponseEntity.ok("{\"message\": \"Se ha realizado correctamente la acción\"}");
        } catch (ClientAlreadyExistsException e) {
            return ResponseEntity.status(409).body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(404).body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (InvalidClientException e) {
            return ResponseEntity.status(400).body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Método para borrar una {@link Client}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Client")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            this.clientService.delete(id);
            return ResponseEntity.ok("{\"message\": \"Se ha realizado correctamente el borrado\"}");
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(404).body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\": \"Error interno del servidor\"}");
        }
    }
}