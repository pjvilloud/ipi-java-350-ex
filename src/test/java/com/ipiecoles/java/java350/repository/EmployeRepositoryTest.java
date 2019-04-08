package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatriculeEmpty(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertNull(lastMatricule);
    }

    @Test
    public void testFindLastMatriculeSingle(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));


        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("12345", lastMatricule);
    }

    @Test
    public void testFindLastMatriculeMultiple(){
        //Given

        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("40325", lastMatricule);
    }

    @Test
    public void testAugmenterSalaireIsZero(){
        //Given
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        Employe employe = employeRepository.findByMatricule("C06432");
        Double salaireTest =  employe.getSalaire();
        //When
        employe.augmenterSalaire(0);
        //Then
        Assertions.assertEquals(salaireTest,employe.getSalaire());
    }

    @Test
    public void testAugmenterSalaireIsNegatif(){
        //Given
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        Employe employe = employeRepository.findByMatricule("C06432");
        Double salaireTest =  employe.getSalaire();
        //When

        employe.augmenterSalaire(-0.1);

        //Then
        Assertions.assertEquals(salaireTest,employe.getSalaire());
    }

    @Test
    public void testAugmenterSalaireIsPositif(){
        //Given
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        Employe employe = employeRepository.findByMatricule("C06432");
        Double salaireTest =  employe.getSalaire();

        //When
        employe.augmenterSalaire(0.1);

        //then
        Assertions.assertTrue(salaireTest < employe.getSalaire());
    }

    @Test
    public void testAugmenterSalaireIsNull(){
        //Given
        Double salaireTest1 = null;
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), null, 1, 1.0));
        Employe employe = employeRepository.findByMatricule("C06432");
        Double salaireTest =  employe.getSalaire();
        //When
        if(salaireTest != null){
            employe.augmenterSalaire(0.1);
        }
        //then
        Assertions.assertNull(employe.getSalaire());
    }



}