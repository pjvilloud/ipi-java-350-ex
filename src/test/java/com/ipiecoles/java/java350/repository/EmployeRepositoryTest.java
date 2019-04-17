package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup() {
        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatriculeNull(){
        //given

        //when
        String lastMatricule = employeRepository.findLastMatricule();
        //then
        assertNull(lastMatricule);
    }

    @Test
    public void testFindLastMatriculeSingle(){
        //given
        Employe e = new Employe("Doe", "John", "T12345",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(e);
        //when
        String lastMatricule = employeRepository.findLastMatricule();
        //then
        assertEquals("12345", lastMatricule);
    }

    @Test
    public void testFindLastMatriculeMultiple(){
        //given
        employeRepository.save( new Employe("Doe", "John", "T12345",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        employeRepository.save( new Employe("Doe", "John", "M40325",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        employeRepository.save( new Employe("Doe", "John", "C06432",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        //when
        String lastMatricule = employeRepository.findLastMatricule();
        //then
        assertEquals("40325", lastMatricule);
    }

    @Test
    public void testIntegrationAvgPerformanceWhereMatriculeStartsWith() {
        // Given
        Employe e = new Employe("Doe", "John", "C12345",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 13, 1.0);
        Employe e1 = new Employe("Doe", "John", "T12345",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0);
        Employe e2 = new Employe("Doe", "John", "C12346",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(e);
        employeRepository.save(e1);
        employeRepository.save(e2);

        // When
        Double avgPerf = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        // Then
        Assertions.assertEquals(new Double(7), avgPerf);
    }

}
