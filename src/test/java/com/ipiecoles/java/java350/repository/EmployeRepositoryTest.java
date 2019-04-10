package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;


// permet de charger un environnement de test spring
// on peut remplacer ces deux lignes par @SpringBootTest
//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach // la méthode annotée ainsi sera exécutée avant chaque test de ce fichier
    @AfterEach // facultatif mais vraiment pour être sûr
    //ces deux annotations sont facultatives quand on utilise @DataJpaTest mais pas quand on utilise @SpringBootTest
    public void setup() {
        employeRepository.deleteAll();
    }

    // test si pas d'employé dans la base
    @Test
    public void testFindLastMatriculeNull(){
        //given

        //when
        String lastMatricule = employeRepository.findLastMatricule();
        //then
        assertNull(lastMatricule);
    }

    // test si un seul employé
    @Test
    public void testFindLastMatriculeSingle(){
        //given
        Employe e = new Employe("Doe", "John", "T12345",
                LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0);
        employeRepository.save(e); // là on écrit sur la base de donnée virtuelle créée, pas dans notre BDD réelle
        // la base de donnée est réinitialisée entre chaque run de test,
        // mais pas entre chaque tests contenus dans ce fichier
        //when
        String lastMatricule = employeRepository.findLastMatricule();
        //then
        assertEquals("12345", lastMatricule);
    }

    // test si un plusieurs employés
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
