package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.service.EmployeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

@DataJpaTest
public class EmployeRepositoryTest {
    @Autowired
    EmployeRepository employeRepository;

    @Mock
    EmployeRepository employeRepository2;

    @InjectMocks
    EmployeService employeService;

    @BeforeEach //fonctionne même si l'utilisateur à oublier de supprimer les données
    void setUp() {
        employeRepository.deleteAll();
    }

    @Test
    void findLastMatriculeEmploye() {
        //Given
        Employe employe1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 2000d, 1, 1.0);
        Employe employe2 = new Employe("black", "Kevin", "C67890", LocalDate.now(), 5000d, 4, 2.0);
        Employe employe3 = new Employe("Purle", "Steve", "M45678", LocalDate.now(), 4000d, 2, 1.0);

        employeRepository.save(employe1);
        employeRepository.save(employe2);
        employeRepository.save(employe3);

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //then
        org.assertj.core.api.Assertions.assertThat(lastMatricule).isEqualTo("67890");
    }
}
