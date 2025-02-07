package com.example.SS.service;

import com.example.SS.entities.PersonPrinciple;
import com.example.SS.entities.Person;
import com.example.SS.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepository repo;

    public PersonServiceImpl(PersonRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = repo.findByEmail(email);
        if(person==null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return new PersonPrinciple(person);
    }

}
