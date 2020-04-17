package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.*;


import java.time.LocalDate;


@DataJpaTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    void setUp() {
        employeRepository.deleteAll();
    }

    @Test
    void findLastMatricule() {
        // Given

        // When
        String lastMatricule = employeRepository.findLastMatricule();
        // Then
        Assertions.assertNull(lastMatricule);
    }
    @Test
    void testFindLastMatricule3Employes() {
        // Given
        employeRepository.save(new Employe("Doe", "John", "T12345",
                LocalDate.now(), 2000d, 1 , 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "C67890",
                LocalDate.now(), 2000d, 1 , 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M45678",
                LocalDate.now(), 2000d, 1 , 1.0));
        //When
        String lastMatricule = employeRepository.findLastMatricule();
        //Then
        Assertions.assertEquals("67890", lastMatricule);
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWith() {
        // Given
        employeRepository.save(new Employe("Doe", "John", "C0001",
                LocalDate.now(), 2000d, 1 , 1.0));
        employeRepository.save(new Employe("Doe", "John", "C0002",
                LocalDate.now(), 2000d, 4 , 1.0));
        employeRepository.save(new Employe("Doe", "John", "C0003",
                LocalDate.now(), 2000d, 6 , 1.0));
        // When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        // Then
        Assertions.assertEquals(3.6666666666666665, avg);
    }
    @Test
    void avgPerformanceWhereMatriculeStartsWithNull() {
        // Given
        employeRepository.save(new Employe("Doe", "John", "C0001",
                LocalDate.now(), 2000d, null, 1.0));
        employeRepository.save(new Employe("Doe", "John", "C0002",
                LocalDate.now(), 2000d, null, 1.0));
        employeRepository.save(new Employe("Doe", "John", "C0003",
                LocalDate.now(), 2000d, null, 1.0));
        // When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        // Then
        Assertions.assertEquals(null, avg);
    }
}