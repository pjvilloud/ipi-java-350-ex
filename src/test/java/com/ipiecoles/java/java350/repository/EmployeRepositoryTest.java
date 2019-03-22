package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// Tests de repository
// Créer la classe de test et les méthodes permettant de tester la méthode findLastMatricule de EmployeRepository.

@SpringBootTest

public class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatricule(){
        //Given


        //When

        String lastMatricule = employeRepository.findLastMatricule();

        //Then

        assertNull(lastMatricule);
    }

    @Test
    public void testFindLastMatriculeSingle(){
        //Given
        Employe e = new Employe("Doe", "John", "T12345", LocalDate.now(),  Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(e);

        String lastMatricule = employeRepository.findLastMatricule();

        assertEquals("12345", lastMatricule);
    }

    @Test
    public void testFindLastMatriculeMultiple(){
        //Given
        Employe e = new Employe("Doe", "John", "T12345", LocalDate.now(),  Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(e);
        Employe f = new Employe("Roa", "Walter", "C45678", LocalDate.now(),  Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(f);
        Employe g = new Employe("Benitez", "Diana", "M23456", LocalDate.now(),  Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(g);

        String lastMatricule = employeRepository.findLastMatricule();

        assertEquals("45678", lastMatricule);
    }
}
