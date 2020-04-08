package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.service.EmployeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

@DataJpaTest
public class EmployeRepositoryTest {
    @Autowired
    EmployeRepository employeRepository;

    @Mock
    EmployeRepository employeRepository2;

    @InjectMocks
    EmployeService employeService;

   @BeforeEach //fonctionne même si l'utilisateur à oublier de supprimer les données
    void setUp() {
        employeRepository.deleteAll();
    }

    @Test
    void findLastMatriculeEmploye() {
        //Given
        Employe employe1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 2000d, 1, 1.0);
        Employe employe2 = new Employe("black", "Kevin", "C67890", LocalDate.now(), 5000d, 4, 2.0);
        Employe employe3 = new Employe("Purle", "Steve", "M45678", LocalDate.now(), 4000d, 2, 1.0);

        employeRepository.save(employe1);
        employeRepository.save(employe2);
        employeRepository.save(employe3);

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //then
        org.assertj.core.api.Assertions.assertThat(lastMatricule).isEqualTo("67890");
    }


    /**
     * Eval
     */

    @ParameterizedTest
    @CsvSource({"'C00011', 2000, 2500, Le matricule C00011 n'existe pas !",
            "'C00011',, 2500, Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !",
            "'C00011',2000,, Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !",
            "'M00011',2000, 2500, Le matricule ne peut être null et doit commencer par un C !",
            "'C00012', 2000, 2500, Le matricule C00012 n'existe pas !",
            "'C00012', -2000, 2500, Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !",
            "'C00012', 2000, -2500, Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !",
            "'C00012', -2000, -2500, Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !",
            ", 2000, 2500, Le matricule ne peut être null et doit commencer par un C !",
    })
    void calculPerformanceCommercialNotFoundTest(String matricule, Long caTraite, Long objectifCa, String result) throws EmployeException {
        //Given
        if((caTraite != null && caTraite != -2000) && (objectifCa != null && objectifCa != -2500) && (matricule != null && !matricule.equals("M00011"))) {
            Mockito.when(employeRepository2.findByMatricule(matricule)).thenReturn(null);
        }
        //When
        EmployeException e = Assertions.assertThrows(EmployeException.class, () ->  employeService.calculPerformanceCommercial(matricule,caTraite , objectifCa));
        //Then
        org.junit.jupiter.api.Assertions.assertEquals(e.getMessage(), result);
    }
    @ParameterizedTest
    @CsvSource({
            "'C00011', 800, 2500, 1",
            "'C00011', 2000, 2500, 1",
            "'C00011', 2200, 2500, 1",
            "'C00011', 2499, 2500, 1",
            "'C00011', 2500, 2500, 1",
            "'C00011', 2502, 2500, 1",
            "'C00011', 2550, 2500, 1",
            "'C00011', 2600, 2500, 1",
            "'C00012', 3000, 2500, 1",
            "'C00013', 10000, 2500, 1",
    })
    void calculPerformanceCommercialNotFoundTest2(String matricule, Long caTraite, Long objectifCa, Integer result) throws EmployeException {
        //Given
        Employe employe = new Employe("Delacour", "Michel", "T00001", LocalDate.now(), 1825.46, 1, null);
        Mockito.when(employeRepository2.findByMatricule(matricule)).thenReturn(employe);
        if (matricule.equals("C00012")) {
            Mockito.when( employeRepository2.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(40D);
        } else if (matricule.equals("C00013")) {
            Mockito.when( employeRepository2.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        } else {
            Mockito.when( employeRepository2.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1D);
        }
        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        Employe newEmploye = new Employe("Delacour", "Michel", "T00001", LocalDate.now(), 1825.46, 1, null);
        Mockito.when(employeRepository2.findByMatricule(matricule)).thenReturn(newEmploye);
        //Then
        Assertions.assertEquals(employeRepository2.findByMatricule(matricule).getPerformance(), result);
    }
}
