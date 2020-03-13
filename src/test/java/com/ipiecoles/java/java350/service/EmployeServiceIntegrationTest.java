package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class EmployeServiceIntegrationTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired EmployeService employeService;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    public void testEmbaucheEmploye(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), 1200d, 1, 1.0));


    }
}
