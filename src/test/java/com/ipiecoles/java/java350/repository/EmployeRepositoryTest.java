package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.time.LocalDate;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    //Sert à effacer les données avant (et après) les tests car elles sont conservées en mémoire, permet aux tests de bien passer.
    @BeforeEach
    @AfterEach
    public void before(){

        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatricule(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    public void testFindLastMatriculeEmployes(){
        //Given, on cree des employes avec des donnees et on sauvegarde le tout temporairement
        Employe e1 = new Employe("Doe","John","T12345", LocalDate.now(),1500d,1,1.0);
        Employe e2 = new Employe("Doe","John","T67891", LocalDate.now(),1500d,1,1.0);
        Employe e3 = new Employe("Doe","John","T01213", LocalDate.now(),1500d,1,1.0);

        employeRepository.save(e1);

        //When, on joue la fonction find last matricule dont on entre la valeur dans lastMatricule
        String lastMatricule = employeRepository.findLastMatricule();

        //Then, on teste qu'il est égal à l'une des valeurs testées.
        Assertions.assertThat(lastMatricule).isEqualTo("12345");
    }

}