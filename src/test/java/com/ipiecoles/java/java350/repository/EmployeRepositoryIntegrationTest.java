package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeRepositoryIntegrationTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWith(){
        //Given
        employeRepository.save(new Employe("Portnoy", "Mike", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Labrie", "James", "C23456", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        employeRepository.save(new Employe("Rudess", "Jordan", "C34567", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Petrucci", "John", "C45678", LocalDate.now(),Entreprise.SALAIRE_BASE, 4, 1.0));

        //When
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertEquals((Double)3.0, avgPerformance);
    }
}
