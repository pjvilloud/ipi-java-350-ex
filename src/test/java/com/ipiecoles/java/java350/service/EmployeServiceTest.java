package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.PotentialStubbingProblem;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeServiceTest {

//    @InjectMocks
    @Autowired
    EmployeService employeService;

    // Remplace la vrai implémentation du repo par un fictif de test :
    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    void setup() {
        employeRepository.deleteAll();
    }

    @Test
    @ExtendWith(SpringExtension.class)
    void embaucheEmploye() throws EmployeException {
        //Given
        // Quand la méthode findLast va etre appelée, on veut qu elle renvoie null pour simuler une base employé vude
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
        employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.LICENCE, 1.0);
    }

    @Test
    @ExtendWith(SpringExtension.class)
    void embaucheEmploye2() throws EmployeException {
        //Given
        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
        Mockito.when(employeRepository.findByMatricule("M45679")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertEquals(employe.getNom(), "Doe");
   }

   @Test
   void calculPerformanceCommercialNotFoundTest() {

       //Given
       // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
//       employeRepository.deleteAll();
       Employe e1 = new Employe("Doe", "John", "C00011", LocalDate.now(), 1050d, 1, 1d);
       employeRepository.save(e1);

//       Mockito.when(employeRepository.findByMatricule("C00011")).thenReturn(e1);

       //When
       PotentialStubbingProblem e = Assertions.assertThrows(PotentialStubbingProblem.class, () -> employeService.calculPerformanceCommercial("C00012",2000L , 2500L));
        System.out.println("---------------------------");
        System.out.println(e.getMessage());
        System.out.println("---------------------------");
       //Then
       Assertions.assertEquals(e.getMessage(), "\n" +
               "Strict stubbing argument mismatch. Please check:\n" +
               " - this invocation of 'findByMatricule' method:\n" +
               "    employeRepository.findByMatricule(\"C00012\");\n" +
               "    -> at com.ipiecoles.java.java350.service.EmployeService.calculPerformanceCommercial(EmployeService.java:103)\n" +
               " - has following stubbing(s) with different arguments:\n" +
               "    1. employeRepository.findByMatricule(\"C00011\");\n" +
               "      -> at com.ipiecoles.java.java350.service.EmployeServiceTest.calculPerformanceCommercialNotFoundTest(EmployeServiceTest.java:83)\n" +
               "Typically, stubbing argument mismatch indicates user mistake when writing tests.\n" +
               "Mockito fails early so that you can debug potential problem easily.\n" +
               "However, there are legit scenarios when this exception generates false negative signal:\n" +
               "  - stubbing the same method multiple times using 'given().will()' or 'when().then()' API\n" +
               "    Please use 'will().given()' or 'doReturn().when()' API for stubbing.\n" +
               "  - stubbed method is intentionally invoked with different arguments by code under test\n" +
               "    Please use default or 'silent' JUnit Rule (equivalent of Strictness.LENIENT).\n" +
               "For more information see javadoc for PotentialStubbingProblem class.");
   }


    @Test
    void calculPerformanceCommercialNormalTest() throws EmployeException {

        //Given
        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
//       employeRepository.deleteAll();
        Employe e1 = new Employe("Doe", "John", "C00011", LocalDate.now(), 1050d, 1, 1d);
        Employe e2 = new Employe("Doe", "John", "C00011", LocalDate.now(), 1050d, 1, 1d);

//        Mockito.when(employeRepository.findByMatricule("C00011")).thenReturn(e1);

        //When
        employeService.calculPerformanceCommercial("C00011",2000L , 2500L);
        Mockito.when(employeRepository.findByMatricule("C00011")).thenReturn(e2);

        //Then
        Assertions.assertEquals(employeRepository.findByMatricule("C00011").getPerformance(), 1);
    }

    @Test
    void calculPerformanceCommercialIntegrationTest() throws EmployeException {

        //Given
        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
//       employeRepository.deleteAll();
        Employe e1 = new Employe("Doe", "John", "C00011", LocalDate.now(), 1050d, 1, 1d);
        employeRepository.save(e1);

        //When
        employeService.calculPerformanceCommercial("C00011",2000L , 2500L);

        //Then
        Assertions.assertEquals(employeRepository.findByMatricule("C00011").getPerformance(), 1);
    }
}