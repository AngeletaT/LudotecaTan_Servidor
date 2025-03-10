package com.ccsw.tutorial.security;

import com.ccsw.tutorial.entities.Admin;
import com.ccsw.tutorial.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDetailsService implements UserDetailsService {
        @Autowired
        private AdminRepository adminRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Admin admin = adminRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with username: " + username));

                List<SimpleGrantedAuthority> authorities = admin.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role))
                                .collect(Collectors.toList());

                return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(),
                                authorities);
        }

}
