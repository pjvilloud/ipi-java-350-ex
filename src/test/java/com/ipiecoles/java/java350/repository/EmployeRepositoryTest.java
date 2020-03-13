package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class EmployeRepositoryTest {

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
    public void testFindEmployesGagnantMoinsQue() {
        //Given
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0);
        Employe e2 = new Employe("Doe", "Jane", "C45678", LocalDate.now(), 2500d, 2, 0.5);
        Employe e3 = new Employe("Doe", "Peter", "M34567", LocalDate.now(), 3500d, 1, 0.5);
        employeRepository.saveAll(Arrays.asList(e1, e2, e3));
        //When
        List<Employe> employeList = employeRepository.findEmployeGagnantMoinsQue("M34567");
        //Then
        org.assertj.core.api.Assertions.assertThat(employeList).containsOnly(e1, e2);
    }

    @Test
    public void testFindEmployesGagnantMoinsQueMatriculeInconnu() {
        //Given
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0);
        Employe e2 = new Employe("Doe", "Jane", "C45678", LocalDate.now(), 2500d, 2, 0.5);
        Employe e3 = new Employe("Doe", "Peter", "M34567", LocalDate.now(), 3500d, 1, 0.5);
        employeRepository.saveAll(Arrays.asList(e1, e2, e3));
        //When
        List<Employe> employeList = employeRepository.findEmployeGagnantMoinsQue("T00001");
        //Then
        org.assertj.core.api.Assertions.assertThat(employeList).isEmpty();
    }
}
