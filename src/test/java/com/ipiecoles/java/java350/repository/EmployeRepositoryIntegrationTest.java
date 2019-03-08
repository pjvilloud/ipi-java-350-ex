package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeRepositoryIntegrationTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void before() {
        employeRepository.deleteAll();
        Employe e = new Employe("test1", "test1", "C00001",LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0D);
        employeRepository.save(e);
        employeRepository.save(new Employe("test2", "test2", "C00002",LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0D));
        employeRepository.save(new Employe("test3", "test3", "C00003",LocalDate.now(), Entreprise.SALAIRE_BASE, 5, 1.0D));
        employeRepository.save(new Employe("test4", "test4", "C00004",LocalDate.now(), Entreprise.SALAIRE_BASE, 7, 1.0D));
        employeRepository.save(new Employe("test5", "test5", "M00005",LocalDate.now(), Entreprise.SALAIRE_BASE, 5, 1.0D));
        employeRepository.save(new Employe("test6", "test6", "T00006",LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0D));
    }

    @Test
    void testAvgPerformanceWhereMatriculeLettreALaCon() {
        //Given
        //When
        Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith("B");
        //Then
        Assertions.assertNull(result);
    }

    @Test
    void testAvgPerformanceWhereMatriculePlusieursLettres() {
        //Given
        //When
        Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith("CC");
        //Then
        Assertions.assertNull(result);
    }

    @Test
    void testAvgPerformanceWhereMatriculeNull() {
        //Given
        //When
        Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith(null);
        //Then
        Assertions.assertNull(result);
    }

    @Test
    void testAvgPerformanceWhereMatriculeStartsWithC() {
        //Given
        Double expected = (1d + 3d + 5d + 7d) / 4d;
        //When
        Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        //Then
        Assertions.assertEquals(expected, result);
    }

}