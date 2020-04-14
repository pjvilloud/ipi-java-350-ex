package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class) // Junit 5
@SpringBootTest
public class EmployeRepositoryTestIntegration {

    @Autowired
    EmployeRepository employeRepository;

    // avant et apres chaque test :
    @BeforeEach
    @AfterEach
    //Supprimer les données qui se trouve dans le repository, pour être sur que notre repository prendra en compte notre given
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWithTest(){

        // Given
        String premiereLettreMattricule = "M";

        Employe employe = new Employe("Delacour", "Michel", "M00001", LocalDate.now(), 1825.46, 2, null);
        employeRepository.save(employe);
        Employe employe2 = new Employe("Mouch", "Jean", "M00002", LocalDate.now(), 2500D, 2, null);
        employeRepository.save(employe);
        employeRepository.save(employe2);

        // When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith(premiereLettreMattricule);

        // Then
        Assertions.assertEquals(2D, avg);
    }
}