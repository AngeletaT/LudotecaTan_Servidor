package com.ccsw.tutorial.service.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.dto.client.ClientDto;
import com.ccsw.tutorial.entities.Client;
import com.ccsw.tutorial.exceptions.client.ClientNotFoundException;
import com.ccsw.tutorial.exceptions.client.ClientAlreadyExistsException;
import com.ccsw.tutorial.exceptions.client.InvalidClientException;
import com.ccsw.tutorial.repository.ClientRepository;

import jakarta.transaction.Transactional;

/**
 * Implementation of ClientService interface.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) throws ClientAlreadyExistsException, InvalidClientException {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new InvalidClientException("El nombre del cliente no puede estar vacío.");
        }

        Optional<Client> existingClient = this.clientRepository.findByName(dto.getName());
        if (existingClient.isPresent() && (id == null || !existingClient.get().getId().equals(id))) {
            throw new ClientAlreadyExistsException("Ya existe un cliente con el mismo nombre");
        }

        Client client;
        if (id == null) {
            client = new Client();
        } else {
            client = this.get(id);
        }
        client.setName(dto.getName());
        this.clientRepository.save(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws ClientNotFoundException {
        if (this.get(id) == null) {
            throw new ClientNotFoundException("Cliente no encontrado.");
        }
        this.clientRepository.deleteById(id);
    }
}