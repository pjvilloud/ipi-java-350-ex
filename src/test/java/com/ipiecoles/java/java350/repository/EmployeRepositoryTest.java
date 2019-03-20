package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
public class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach //Réalise la méthode ci-dessous AVANT chaque test - A voir selon nos préférences
    @AfterEach  //Réalise la méthode ci-dessous APRES chaque test - A voir selon nos préférences
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatriculeWithNoEmploye(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();

    }

    @Test
    public void testFindLastMatriculeAfterCreateNewEmploye(){
        //Given
        employeRepository.save(
                new Employe("Doe", "John", "M00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0)
        );


        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("00001");

    }

    @Test
    public void testFindLastMatriculeAfterCreateManyEmployes(){
        //Given
        employeRepository.save(
                new Employe("Doe", "Jane", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0)
        );
        employeRepository.save(
                new Employe("Doe", "John", "M48789", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0)
        );
        employeRepository.save(
                new Employe("Doe", "Jim", "C00002", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0)
        );

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("48789");

    }

}
