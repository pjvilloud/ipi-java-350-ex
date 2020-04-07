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
       EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial("C00012",2000L , 2500L));

       //Then
       Assertions.assertEquals(e.getMessage(), "Le matricule C00012 n'existe pas !");
   }

}