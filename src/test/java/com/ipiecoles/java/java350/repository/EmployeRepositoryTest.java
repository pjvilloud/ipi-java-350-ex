package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
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
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("40325", lastMatricule);
    }

    @Test
    public void IntegrationAvgPerformanceWhereMatriculeStartsWith() {
        //Given
        employeRepository.save(new Employe("Doe", "John", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Snow", "John", "C99999", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 0.5));
        employeRepository.save(new Employe("Toto", "John", "C99998", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));
        employeRepository.save(new Employe("Tutu", "John", "C99997", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 0.5));
        employeRepository.save(new Employe("Titi", "John", "C99996", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Tata", "John", "C99995", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));


        //When
        Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        // performance moyenne = (1+2+4+1+1+3)/6 = 2
        Assertions.assertEquals(2, performanceMoyenne.doubleValue());

    }
}
