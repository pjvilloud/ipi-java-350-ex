package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    public void setup(){
        employeRepository.deleteAll();
    }

    public void testIntegrationEmploye(){
        //Given

        //When

        //Then
    }
}
