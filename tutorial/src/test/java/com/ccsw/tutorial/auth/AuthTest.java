package com.ccsw.tutorial.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ccsw.tutorial.dto.auth.LoginRequestDto;
import com.ccsw.tutorial.dto.auth.RegisterRequestDto;
import com.ccsw.tutorial.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Test que registra un nuevo admin y posteriormente permite el login con las
    // mismas credenciales
    @Test
    public void testRegisterAndSigninSuccess() throws Exception {
        // Limpieza o asegurarse de que no exista previamente el usuario de prueba
        adminRepository.deleteAll();

        RegisterRequestDto registerRequest = new RegisterRequestDto("testadmin", "password123");

        // Registro
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User registered"));

        // Inicio de sesión con las mismas credenciales
        LoginRequestDto loginRequest = new LoginRequestDto("testadmin", "password123");
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    // Test que verifica que no se pueda registrar un usuario con un username
    // existente
    @Test
    public void testRegisterWithExistingUsername() throws Exception {
        // Limpieza previa
        adminRepository.deleteAll();

        RegisterRequestDto registerRequest = new RegisterRequestDto("existingadmin", "password123");

        // Primer registro
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User registered"));

        // Intento de registro con el mismo username
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    // Test que verifica que el inicio de sesión con credenciales incorrectas
    // devuelva error 401
    @Test
    public void testSigninWithWrongCredentials() throws Exception {
        // Limpieza previa para asegurar que no exista el usuario
        adminRepository.deleteAll();

        LoginRequestDto loginRequest = new LoginRequestDto("nonexistent", "wrongpassword");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}
