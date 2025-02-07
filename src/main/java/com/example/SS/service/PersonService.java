package com.example.SS.service;


import com.example.SS.entities.Person;
import com.example.SS.repository.PersonRepository;
import com.example.SS.util.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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


    public String verify(Person person) {
        Authentication authentication = manager.authenticate
                (new UsernamePasswordAuthenticationToken(person.getEmail(),person.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtservice.generateToken(person.getEmail(),person.getRole());
        }
        else{
            throw new RuntimeException("Invalid Username and Password");
        }
    }

}
