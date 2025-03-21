package com.ccsw.tutorial.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.dto.client.ClientDto;
import com.ccsw.tutorial.entities.Client;
import com.ccsw.tutorial.exceptions.client.ClientAlreadyExistsException;
import com.ccsw.tutorial.exceptions.client.ClientNotFoundException;
import com.ccsw.tutorial.exceptions.client.InvalidClientException;
import com.ccsw.tutorial.repository.ClientRepository;
import com.ccsw.tutorial.service.client.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClientTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    // TEST LIST ALL CLIENTS
    @Test
    public void findAllShouldReturnAllClients() {

        List<Client> list = new ArrayList<>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    // TEST SAVE CLIENT
    public static final String CLIENT_NAME = "CAT1";

    @Test
    public void saveNotExistsClientIdShouldInsert() throws ClientAlreadyExistsException, InvalidClientException {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        clientService.save(null, clientDto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    // TEST UPDATE CLIENT
    public static final Long EXISTS_CLIENT_ID = 1L;

    @Test
    public void saveExistsClientIdShouldUpdate() throws ClientAlreadyExistsException, InvalidClientException {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.save(EXISTS_CLIENT_ID, clientDto);

        verify(clientRepository).save(client);
    }

    // TEST DELETE CLIENT
    @Test
    public void deleteExistsClientIdShouldDelete() throws ClientNotFoundException {

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.delete(EXISTS_CLIENT_ID);

        verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }

    // TEST GET CLIENT
    public static final Long NOT_EXISTS_CLIENT_ID = 0L;

    @Test
    public void getExistsClientIdShouldReturnClient() {

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(EXISTS_CLIENT_ID);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        Client clientResponse = clientService.get(EXISTS_CLIENT_ID);

        assertNotNull(clientResponse);
        assertEquals(EXISTS_CLIENT_ID, client.getId());
    }

    @Test
    public void getNotExistsClientIdShouldThrowException() {

        when(clientRepository.findById(NOT_EXISTS_CLIENT_ID)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            clientService.get(NOT_EXISTS_CLIENT_ID);
        });
    }

    // TEST DUPLICATE CLIENT NAME
    @Test
    public void saveDuplicateClientNameShouldThrowException() {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client existingClient = new Client();
        existingClient.setId(EXISTS_CLIENT_ID);
        existingClient.setName(CLIENT_NAME);

        when(clientRepository.findByName(CLIENT_NAME)).thenReturn(Optional.of(existingClient));

        assertThrows(ClientAlreadyExistsException.class, () -> {
            clientService.save(null, clientDto);
        });
    }

    // TEST INVALID CLIENT NAME
    @Test
    public void saveInvalidClientNameShouldThrowException() {

        ClientDto clientDto = new ClientDto();
        clientDto.setName("");

        assertThrows(InvalidClientException.class, () -> {
            clientService.save(null, clientDto);
        });
    }
}
