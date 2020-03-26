package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    // avant et apres chaque test :
    @BeforeEach
    @AfterEach
    void setUp(){
        employeRepository.deleteAll();
    }

    @Test
    void findLastMatricule() {
        // Given

        // When
        String lastMarticule = employeRepository.findLastMatricule();

        // Then
    }

    @Test
    void findLastMatricule2() {
        // Given
        employeRepository.deleteAll();
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1050d, 1, 1d);
        Employe e2 = new Employe("TEST", "aa", "T67891", LocalDate.now(), 1050d, 1, 1d);
        Employe e3 = new Employe("wo", "nom", "T54876", LocalDate.now(), 1050d, 1, 1d);

        // When
        String lastMarticule = employeRepository.findLastMatricule();

        // Then
    }
}