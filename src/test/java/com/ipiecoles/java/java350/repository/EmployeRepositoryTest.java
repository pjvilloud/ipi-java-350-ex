package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;

public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    EmployeService employeService;

    @Test
    public void testFindLastMatricule(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    public void testFindLastMatricule3Employes(){
        //Given
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1200d, 1, 1.0);
        Employe e2 = new Employe("Pierre", "Jean", "T67891", LocalDate.now(), 2500d, 2, 2.0);
        Employe e3 = new Employe("Mare", "Jacky", "T011121", LocalDate.now(), 1800d, 1, 1.6);

        employeRepository.saveAll(Arrays.asList(e1,e2,e3));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    public void testPassageTempsPartiel(){
        //Given
        Employe e = new Employe("Mare", "Jacky", "T011121", LocalDate.now(), 1800d, 1, 1.0);
        employeRepository.save(e);

        //When

        List<Employe> employeList = employeRepository.findEmployeByGagnantMoinsQue("T12345");

        //Then
        Assertions.assertThat(employeList.size()).isNotZero();
    }

    /*@Test
    public void testCalculSalaureMoyenTP() throws Exception{
        //Given
        Mockito.when(employeRepository.count()).thenReturn(1000d);

        //When
        Throwable exception = Assertions.catchThrowable(() -> employeService.calculSalaireMoyenETP());
        Double salaireMoyen = employeService.calculSalaireMoyenETP();

        //Then
        Assertions.assertThat(salaireMoyen).isEqualTo(1000d);
    }*/

    @Test
    public void testFindEmployeByGagnantMoinsQue(){
        //Given
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1200d, 1, 1.0);
        Employe e2 = new Employe("Pierre", "Jean", "T67891", LocalDate.now(), 2500d, 2, 2.0);
        Employe e3 = new Employe("Mare", "Jacky", "T011121", LocalDate.now(), 1800d, 1, 1.6);

        employeRepository.saveAll(Arrays.asList(e1,e2,e3));

        //When
        List<Employe> employeList = employeRepository.findEmployeByGagnantMoinsQue("T12345");

        //Then
        Assertions.assertThat(employeList.size()).isNotZero();
    }

    @Test
    public void testFindEmployeByGagnantMoinsQueIsNull(){
        //Given
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1200d, 1, 1.0);
        Employe e2 = new Employe("Pierre", "Jean", "T67891", LocalDate.now(), 2500d, 2, 2.0);
        Employe e3 = new Employe("Mare", "Jacky", "T011121", LocalDate.now(), 1800d, 1, 1.6);

        employeRepository.saveAll(Arrays.asList(e1,e2,e3));

        //When
        List<Employe> employeList = employeRepository.findEmployeByGagnantMoinsQue("T67891");

        //Then
        Assertions.assertThat(employeList.size()).isNull();
    }
}
