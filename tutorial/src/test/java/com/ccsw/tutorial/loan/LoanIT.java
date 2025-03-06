package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.dto.LoanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<List<LoanDto>>() {
    };

    // TEST LIST ALL LOANS
    @Test
    public void findAllShouldReturnAllLoans() {

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // TEST SAVE LOAN
    @Test
    public void saveWithoutIdShouldCreateNewLoan() {

        LoanDto dto = new LoanDto();
        dto.setRentalDate("2025-03-01");
        dto.setReturnDate("2025-03-10");

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // TEST UPDATE LOAN
    public static final Long MODIFY_LOAN_ID = 1L;

    @Test
    public void modifyWithExistIdShouldModifyLoan() {

        LoanDto dto = new LoanDto();
        dto.setRentalDate("2025-03-01");
        dto.setReturnDate("2025-03-10");

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_LOAN_ID, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // TEST DELETE LOAN
    public static final Long DELETE_LOAN_ID = 1L;

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID, HttpMethod.DELETE, null,
                Void.class);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteWithNotExistsIdShouldInternalError() {

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/0",
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
