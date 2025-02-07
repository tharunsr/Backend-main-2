package com.example.SS.controller;

import com.example.SS.entities.Person;
import com.example.SS.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/register")
    public void register(@RequestBody Person person){
        service.register(person);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verify(@RequestBody Person person) {
        String jwtToken = service.verify(person);  // Call service to authenticate

        if (jwtToken == null) {
            return ResponseEntity.status(401).body("Invalid credentials");  // Return 401 if authentication fails
        }

        return ResponseEntity.ok().body(Map.of("token", jwtToken));  // Return token as JSON
    }



}
