package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void test_calculPerformanceCommercial_Cas2PerfomanceMoyenneInf() throws EmployeException {
        //Given
        employeRepository.save(new Employe("Doe", "John", "C123", LocalDate.now(), Entreprise.SALAIRE_BASE, 6, 1.0));
        employeRepository.save(new Employe("Doe", "John", "C234", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));


        //When
        this.employeService.calculPerformanceCommercial("C123",8l,10l);

        //Then
        Employe employe = this.employeRepository.findByMatricule("C123");
        Assertions.assertEquals(5,employe.getPerformance());
    }
}
