package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;

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

    @ParameterizedTest
    @CsvSource({
            "'Doe','John','C12385', 2020-04-08 ,2000d,1,1.0",
            "'Samuel','Jack','M1385', 2020-04-08 ,2050d,1,1.0",
    })
    @Test
    void testavgPerformanceWhereMatriculeStartsWith(String nom, String prenom, String matricule, LocalDate dateEmbauche,Double salaire, Integer perf, Double tp) {
        //Given : données à tester
        Employe eTemp = new Employe(nom,prenom,matricule,dateEmbauche,salaire,perf,tp);
        employeRepository.save(eTemp);

        //When : fonction à tester
        Double resAvg = employeRepository.avgPerformanceWhereMatriculeStartsWith(matricule.substring(0,1)) ;

        //Then : asserThat = le résultat de la fonction , isEqualTo = on regarde si le résultat est bien égal à cela ici ("67890")
        Assertions.assertEquals(1.0, resAvg);
    }
}