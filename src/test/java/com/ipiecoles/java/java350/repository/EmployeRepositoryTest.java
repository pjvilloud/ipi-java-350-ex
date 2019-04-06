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
    public void testAvgPerformanceWhereMatriculeStartsWithCEmpty(){
        //Given

        //When
        Double moyPerfC = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        Double moyPerfM = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
        Double moyPerfT = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        //Then
        Assertions.assertNull(moyPerfC);
        Assertions.assertNull(moyPerfM);
        Assertions.assertNull(moyPerfT);
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithCNoC(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        Double moyPerfC = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        Double moyPerfM = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
        Double moyPerfT = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        //Then
        Assertions.assertEquals(1.0, moyPerfC.doubleValue());
        Assertions.assertEquals(1.0, moyPerfM.doubleValue());
        Assertions.assertNull(moyPerfT);
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWith(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "M12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 5, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Doe", "Tom", "C04432", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));

        //When
        Double moyPerfC = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        Double moyPerfM = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
        Double moyPerfT = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        //Then
        //1+5+3+2=11
        //11/4 = 2.75
        Assertions.assertEquals(2.5, moyPerfC.doubleValue());
        Assertions.assertEquals(3.0, moyPerfM.doubleValue());
        Assertions.assertNull(moyPerfT);
    }
}