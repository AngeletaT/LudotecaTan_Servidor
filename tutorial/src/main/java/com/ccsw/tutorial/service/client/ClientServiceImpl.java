package com.ccsw.tutorial.service.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.dto.client.ClientDto;
import com.ccsw.tutorial.entities.Client;
import com.ccsw.tutorial.exception.ClientAlreadyExistsException;
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
        return this.clientRepository.findById(id).orElse(null);
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
    public void save(Long id, ClientDto dto) throws ClientAlreadyExistsException {
        Optional<Client> existingClient = this.clientRepository.findByName(dto.getName());
        if (existingClient.isPresent() && (id == null || !existingClient.get().getId().equals(id))) {
            throw new ClientAlreadyExistsException("Ya existe un cliente con el mismo nombre");
        }

        Client client;
        if (id == null) {
            client = new Client();
        } else {
            client = this.get(id);
            if (client == null) {
                client = new Client();
            }
        }
        client.setName(dto.getName());
        this.clientRepository.save(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }
        this.clientRepository.deleteById(id);
    }
}