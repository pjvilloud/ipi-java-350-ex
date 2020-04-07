package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
@DataJpaTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    //Réinitialiser  la bdd en mémoire avant chaque test
    @BeforeEach
    @AfterEach
    void setup(){
        employeRepository.deleteAll();
    }

    @Test
    void testFindLastMatricule0Employes() {
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertNull(lastMatricule);
    }

    @Test
    void testFindLastMatricule1Employes() {
        //Given : données à tester
        Employe e1 = new Employe("Doe","John","T12345", LocalDate.now(),2000d,1,1.0);
        Employe e2 = new Employe("Doe","jane","C67890", LocalDate.now(),2000d,1,1.0);
        Employe e3 = new Employe("Toe","ane","M45678", LocalDate.now(),2000d,1,1.0);

        employeRepository.save(e1);
        employeRepository.save(e2);
        employeRepository.save(e3);

        //When : fonction à tester
        String lastMatricule = employeRepository.findLastMatricule();

        //Then : asserThat = le résultat de la fonction , isEqualTo = on regarde si le résultat est bien égal à cela ici ("67890")
        Assertions.assertEquals("67890", lastMatricule);
    }
}