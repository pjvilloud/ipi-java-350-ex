package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void test_avgPerformanceWhereMatriculeStartsWith(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "C123", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));
        employeRepository.save(new Employe("Doe", "John", "C234", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));

        //When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertEquals(3d,avg);
    }
}