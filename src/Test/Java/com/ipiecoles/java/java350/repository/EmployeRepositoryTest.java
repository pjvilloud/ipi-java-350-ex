package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.asserts.Assertion;

import java.time.LocalDate;

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
    public void testFindLastMatriculeEmpty(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    public void testFindLastMatriculeSingle(){
        //Given
        employeRepository.save(new Employe("Hen", "Ry", "T88888", LocalDate.now(), Entreprise.SALAIRE_BASE,1, 1.5));

    //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("88888");
    }


    @Test
    public void testFindLastMatriculeMultiple(){
        //Given
        employeRepository.save(new Employe("Hen", "Ry", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE,1, 1.5));
        employeRepository.save(new Employe("Hen", "Muffin", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE,1, 1.5));
        employeRepository.save(new Employe("Hen", "Tay", "T06432", LocalDate.now(), Entreprise.SALAIRE_BASE,1, 1.5));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("40325");
    }
}

