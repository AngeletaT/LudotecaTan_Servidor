package com.ccsw.tutorial.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.dto.auth.LoginRequestDto;
import com.ccsw.tutorial.dto.auth.LoginResponseDto;
import com.ccsw.tutorial.dto.auth.RegisterRequestDto;
import com.ccsw.tutorial.entities.Admin;
import com.ccsw.tutorial.repository.AdminRepository;
import com.ccsw.tutorial.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ccsw
 *
 */
@Tag(name = "Auth", description = "API of Auth")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Login", description = "Method that authenticates a user and returns a JWT token")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                    loginRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Ese usuario no existe.\"}");
        }

        String token = jwtUtil.generateToken(loginRequestDto.getUsername());
        String username = loginRequestDto.getUsername();
        LoginResponseDto loginResponseDto = new LoginResponseDto(token, username);
        return ResponseEntity.ok(loginResponseDto);
    }

    @Operation(summary = "Register", description = "Method that registers a new user")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto) {
        if (adminRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("{\"message\": \"Un usuario con este nombre ya existe\"}");
        }

        String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");

        Admin admin = new Admin(registerRequestDto.getUsername(), encodedPassword, roles);
        adminRepository.save(admin);

        return ResponseEntity.ok("{\"message\": \"Se ha registrado el usuario correctamente\"}");
    }

    @Operation(summary = "Validate Token", description = "Method that validates a JWT token and returns the username")
    @RequestMapping(path = "/validate", method = RequestMethod.GET)
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.badRequest().body("{\"message\": \"Token inválido\"}");
        }

        String username = jwtUtil.extractUsername(token);
        return ResponseEntity.ok("{\"username\": \"" + username + "\"}");
    }
}