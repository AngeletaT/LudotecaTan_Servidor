package com.ccsw.tutorial.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ccsw.tutorial.dto.auth.LoginRequestDto;
import com.ccsw.tutorial.dto.auth.RegisterRequestDto;
import com.ccsw.tutorial.dto.auth.LoginResponseDto;
import com.ccsw.tutorial.entities.Admin;
import com.ccsw.tutorial.repository.AdminRepository;
import com.ccsw.tutorial.security.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                    loginRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(loginRequestDto.getUsername());
        LoginResponseDto loginResponseDto = new LoginResponseDto(token);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto) {
        if (adminRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");

        Admin admin = new Admin(registerRequestDto.getUsername(), encodedPassword, roles);
        adminRepository.save(admin);

        return ResponseEntity.ok("User registered");
    }
}