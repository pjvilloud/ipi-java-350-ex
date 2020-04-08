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

    //Test nominal
    @Test
    public void testAvgPerformance(){
        Employe emp1 = new Employe();
        emp1.setMatricule("C00001");
        emp1.setPerformance(5);

        Employe emp2 = new Employe();
        emp2.setMatricule("C00002");
        emp2.setPerformance(3);

        Employe emp3 = new Employe();
        emp3.setMatricule("C00003");
        emp3.setPerformance(2);

        Employe emp4 = new Employe();
        emp4.setMatricule("C00004");
        emp4.setPerformance(10);

        employeRepository.save(emp1);
        employeRepository.save(emp2);
        employeRepository.save(emp3);
        employeRepository.save(emp4);

        //When
        Double moyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        //Then
        Assertions.assertEquals(5D, moyenne);
    }

    //Test matricule différent de C
    @Test
    public void testAvgPerformanceMatriculeDifferent(){
        Employe emp1 = new Employe();
        emp1.setMatricule("T00001");
        emp1.setPerformance(5);

        Employe emp2 = new Employe();
        emp2.setMatricule("M00002");
        emp2.setPerformance(3);

        Employe emp3 = new Employe();
        emp3.setMatricule("T00003");
        emp3.setPerformance(2);

        Employe emp4 = new Employe();
        emp4.setMatricule("M00004");
        emp4.setPerformance(10);

        employeRepository.save(emp1);
        employeRepository.save(emp2);
        employeRepository.save(emp3);
        employeRepository.save(emp4);

        //When
        Double moyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        //Then
        Assertions.assertNull(moyenne);
    }
}