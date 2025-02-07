package com.example.SS.service;


import com.example.SS.entities.Person;
import com.example.SS.repository.PersonRepository;
import com.example.SS.util.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    String token ;

    @Autowired
    private JWTService jwtservice;

    @Autowired
    private PersonRepository repo;

    @Autowired
    private AuthenticationManager manager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void register(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        repo.save(person);
    }


    @Autowired
    private PersonRepository userRepository;

    public String verify(Person person) {
        // Authenticate the user
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Retrieve the authenticated user's details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Fetch the user's role (assuming it's a part of user details)
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No role found for user"));

            // Generate JWT token with the user's email and role
            return jwtservice.generateToken(person.getEmail(), role);
        } else {
            throw new RuntimeException("Invalid Username and Password");
        }
    }




}
