package com.example.SS.repository;

import com.example.SS.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {


    Person findByEmail(String email);
}
