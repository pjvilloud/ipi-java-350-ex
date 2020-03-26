package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
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
        Assertions.assertThat(lastMatricule).isNull();
    }
    @Test
    void findLastMatricule3Employes() {
        // Given
        Employe e1 = new Employe("Doe", "John", "T12345",
                LocalDate.now(), 200d, 1 , 1.0);
        Employe e2 = new Employe("Doe", "Jane", "C67890",
                LocalDate.now(), 200d, 1 , 1.0);
        Employe e3 = new Employe("Doe", "Jane", "M45678",
                LocalDate.now(), 200d, 1 , 1.0);
        // When
        String lastMatricule = employeRepository.findLastMatricule();
        // Then
        Assertions.assertThat(lastMatricule).isEqualTo("67890");
    }
}