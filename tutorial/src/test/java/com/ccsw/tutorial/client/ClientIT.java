package com.ccsw.tutorial.client;

import com.ccsw.tutorial.dto.client.ClientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientIT {

        public static final String LOCALHOST = "http://localhost:";
        public static final String SERVICE_PATH = "/client";

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        ParameterizedTypeReference<List<ClientDto>> responseType = new ParameterizedTypeReference<List<ClientDto>>() {
        };

        // TEST LIST ALL CLIENTS
        @Test
        public void findAllShouldReturnAllClients() {

                ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                                HttpMethod.GET, null, responseType);

                assertNotNull(response);
                assertEquals(10, response.getBody().size());
        }

        // TEST SAVE CLIENT
        public static final Long NEW_CLIENT_ID = 11L;
        public static final String NEW_CLIENT_NAME = "CAT11";

        @Test
        public void saveWithoutIdShouldCreateNewClient() {
                ClientDto dto = new ClientDto();
                dto.setName(NEW_CLIENT_NAME);

                restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto),
                                Void.class);

                ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                                HttpMethod.GET, null, responseType);
                assertNotNull(response);
                assertEquals(11, response.getBody().size());

                ClientDto clientSearch = response.getBody().stream()
                                .filter(item -> NEW_CLIENT_NAME.equals(item.getName()))
                                .findFirst().orElse(null);
                assertNotNull(clientSearch);
                assertNotNull(clientSearch.getId());
                assertEquals(NEW_CLIENT_NAME, clientSearch.getName());
        }

        // TEST UPDATE CLIENT
        public static final Long MODIFY_CLIENT_ID = 3L;

        @Test
        public void modifyWithExistIdShouldModifyClient() {

                ClientDto dto = new ClientDto();
                dto.setName(NEW_CLIENT_NAME);

                restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_CLIENT_ID, HttpMethod.PUT,
                                new HttpEntity<>(dto), Void.class);

                ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                                HttpMethod.GET, null, responseType);
                assertNotNull(response);
                assertEquals(10, response.getBody().size());

                ClientDto clientSearch = response.getBody().stream()
                                .filter(item -> item.getId().equals(MODIFY_CLIENT_ID))
                                .findFirst().orElse(null);
                assertNotNull(clientSearch);
                assertEquals(NEW_CLIENT_NAME, clientSearch.getName());
        }

        // TEST UPDATE CLIENT WITH NOT EXIST ID
        @Test
        public void modifyWithNotExistIdShouldInternalError() {

                ClientDto dto = new ClientDto();
                dto.setName(NEW_CLIENT_NAME);

                ResponseEntity<?> response = restTemplate.exchange(
                                LOCALHOST + port + SERVICE_PATH + "/" + NEW_CLIENT_ID,
                                HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        // TEST DELETE CLIENT
        public static final Long DELETE_CLIENT_ID = 2L;

        @Test
        public void deleteWithExistsIdShouldDeleteClient() {

                restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_CLIENT_ID, HttpMethod.DELETE, null,
                                Void.class);

                ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                                HttpMethod.GET, null, responseType);
                assertNotNull(response);
                assertEquals(9, response.getBody().size());
        }

        @Test
        public void deleteWithNotExistsIdShouldInternalError() {

                ResponseEntity<?> response = restTemplate.exchange(
                                LOCALHOST + port + SERVICE_PATH + "/" + NEW_CLIENT_ID,
                                HttpMethod.DELETE, null, Void.class);

                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }
}