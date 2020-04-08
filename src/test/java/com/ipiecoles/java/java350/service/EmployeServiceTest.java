package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    public void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        when(employeRepository.findLastMatricule()).thenReturn("00345");
        when(employeRepository.findByMatricule("T00346")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertEquals("T00346", employeArgumentCaptor.getValue().getMatricule());
        Assertions.assertEquals(tempsPartiel, employeArgumentCaptor.getValue().getTempsPartiel());

        //1521.22 * 1.2 * 1.0
        Assertions.assertEquals(1825.46, employeArgumentCaptor.getValue().getSalaire().doubleValue());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn("00345");
        when(employeRepository.findByMatricule("M00346")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertEquals("M00346", employeArgumentCaptor.getValue().getMatricule());
        Assertions.assertEquals(tempsPartiel, employeArgumentCaptor.getValue().getTempsPartiel());

        //1521.22 * 1.4 * 0.5
        Assertions.assertEquals(1064.85, employeArgumentCaptor.getValue().getSalaire().doubleValue());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterNoLastMatricule() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("M00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals("M00001", employeArgumentCaptor.getValue().getMatricule());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterExistingEmploye(){
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());

        //When/Then
        EntityExistsException e = Assertions.assertThrows(EntityExistsException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD", e.getMessage());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster99999(){
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("Limite des 100000 matricules atteinte !", e.getMessage());
    }

    /*Question : Tester sans dépendance à la BDD la méthode calculPerformanceCommercial d'EmployeService
    * CA négatif ou absent
    */
    @ParameterizedTest
    @CsvSource({
            "'C00001', , 5000",
            "'C00001', -2000, 5000"
    })
    public void testCalculPerformanceCommercialCaNullNegatif(String matricule, Long CaTraite, Long objectifCa) throws EmployeException {

        Exception exception = Assertions.assertThrows(EmployeException.class, () -> {
            employeService.calculPerformanceCommercial(matricule,CaTraite,objectifCa);
        });

        String expectedMessage = "Le chiffre d'affaire traité ne peut être négatif ou null !";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    /*Question : Tester sans dépendance à la BDD la méthode calculPerformanceCommercial d'EmployeService
     * Objectif CA négatif ou absent
     */
    @ParameterizedTest
    @CsvSource({
            "'C00001',2000 ,",
            "'C00001', 2000, -1"
    })
    public void testCalculPerformanceCommercialObjectifCaNullNegatif(String matricule, Long CaTraite, Long objectifCa) throws EmployeException {

        Exception exception = Assertions.assertThrows(EmployeException.class, () -> {
            employeService.calculPerformanceCommercial(matricule,CaTraite,objectifCa);
        });

        String expectedMessage = "L'objectif de chiffre d'affaire ne peut être négatif ou null !";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    /*Question : Tester sans dépendance à la BDD la méthode calculPerformanceCommercial d'EmployeService
     * Matricule incorrect ou inexistant
     */
    @ParameterizedTest
    @CsvSource({
            "'',2000 ,5000",
            "'T00001', 2000, 5000"
    })
    public void testCalculPerformanceCommercialVerificationMatricule(String matricule, Long CaTraite, Long objectifCa) throws EmployeException {

        Exception exception = Assertions.assertThrows(EmployeException.class, () -> {
            employeService.calculPerformanceCommercial(matricule,CaTraite,objectifCa);
        });

        String expectedMessage = "Le matricule ne peut être null et doit commencer par un C !";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }




//    CA = 1000 obj = 10000
//
////Autre cas, donc performance = performanse.base (1) + 1 de moyenne
//            'C00001', 1000, 10000, 5, 1
//
////On lui enlève 2 points de performance
//            'C00001', 8500, 10000, 5, 3
//    Cas 2 :
//    Si CA supérieur ou égale à 80% de l'obectif (8000) et qu'il est inférieur à 95% (9500) - 2 de performance + 1 de moyenne
//
////On ajoute pas de point, la performance est gelée
//'C00001', 10000, 10000, 5, 5
//    Cas 3 :
//    Si CA supérieur ou égale à 95% de l'obectif (9500) et qu'il est inférieur ou égale à 105% (10500) on utilise sa performance en cours  + 1 de moyenne
//
////On ajoute 1  de performance
//'C00001', 11500, 10000, 5, 6
//    Cas 4 :
//    Si CA inférieur ou égale à 120% de l'obectif (12000) et qu'il est supérieur à 105% (10500) on utilise sa performance en cours + 1 + 1 de moyenne
//
////On ajoute 4 de performance
//            'C00001', 15000, 10000, 5, 9
//    Cas 4 :
//    Si CA supérieur à 120% de l'obectif (12000) on utilise sa performance en cours + 4 + 1 de moyenne
    @ParameterizedTest
    @CsvSource({
            "'C00001', 1000, 10000, 5, 2",
            "'C00001', 8500, 10000, 5, 4",
            "'C00001', 10000, 10000, 5, 6",
            "'C00001', 11500, 10000, 5, 7",
            "'C00001', 15000, 10000, 5, 10"
    })
    public void testCalculPerformanceCommercialCas2A4(
            String matricule,
            Long CaTraite,
            Long objectifCa,
            Integer performanceInit,
            Integer performanceAttendue) throws EmployeException{

        Employe employe = new Employe("TEST", "Java", matricule, LocalDate.now(), 1000d, performanceInit, 1.0);
        when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        // When
        employeService.calculPerformanceCommercial(matricule, CaTraite, objectifCa);
        // Then
        Assertions.assertEquals(performanceAttendue, employe.getPerformance());
    }



}