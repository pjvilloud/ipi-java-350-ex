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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;

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
        Assertions.assertEquals(1825.464, employeArgumentCaptor.getValue().getSalaire().doubleValue());
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
        Assertions.assertEquals(1064.854, employeArgumentCaptor.getValue().getSalaire().doubleValue());
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

    @Test
    void testCalculPerformanceCommercialeCaTraiteNull() {
        //GIVEN
        employeRepository.save(new Employe("Doe", "John", "C06432", LocalDate.now(), 1825.464, 1, 1.0));
        String matricule = "C06432";
        Long caTraite = null;
        Long objectifCa = 500L;
        //WHEN
        try {
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("calculPerformanceCommercial aurait dû lancer une exception");
        } catch (EmployeException e) {
            //THEN
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire traité ne peut être négatif ou null !");
        }
    }

    @Test
    void testCalculPerformanceCommercialeCaTraiteNegatif() {

        //GIVEN
        employeRepository.save(new Employe("Doe", "John", "C06432", LocalDate.now(), 1825.464, 1, 1.0));
        String matricule = "C06432";
        Long caTraite = -500L;
        Long objectifCa = 500L;

        //WHEN
        try {
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("calculPerformanceCommercial aurait dû lancer une exception");
        } catch (EmployeException e) {
            //THEN
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire traité ne peut être négatif ou null !");
        }
    }


    @Test
    void testCalculPerformanceCommercialeObjectifCaNull() {
        //GIVEN
        employeRepository.save(new Employe("Doe", "John", "C06432", LocalDate.now(), 1825.464, 1, 1.0));
        String matricule = "C06432";
        Long caTraite = 500L;
        Long objectifCa = null;
        //WHEN
        try {
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("calculPerformanceCommercial aurait dû lancer une exception");
        } catch (EmployeException e) {
            //THEN
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
        }
    }

    @Test
    void testCalculPerformanceCommercialeObjectifCaNegatif() {

        //GIVEN
        employeRepository.save(new Employe("Doe", "John", "C06432", LocalDate.now(), 1825.464, 1, 1.0));
        String matricule = "C06432";
        Long caTraite = 500L;
        Long objectifCa = -500L;

        //WHEN
        try {
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("calculPerformanceCommercial aurait dû lancer une exception");
        } catch (EmployeException e) {
            //THEN
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
        }
    }


    @Test
    void testCalculPerformanceCommercialeMatriculeDontExist() {
        //GIVEN
        String matricule = "C35353";

        Long caTraite = 500L;
        Long objectifCa = 500L;

        //WHEN
        try {
            employeService.calculPerformanceCommercial(matricule,caTraite, objectifCa);
            Assertions.fail("La méthode calculPerformanceCommercial doit lancer une exception");
        } catch (EmployeException e) {
            //THEN
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule C35353 n'existe pas !");
        }
    }


    @ParameterizedTest
    @CsvSource({
            "T00001",
            ", ,"
    })
    void testCalculPerformanceCommercialMatriculeNullOrNotBeginWithCOrDontExist(String matricule) {

        //GIVEN
        Employe employe = new Employe("Doe", "Joe", matricule, LocalDate.now(), 1500d, 1, 1.0);
        Long caTraite = 1000L;
        Long objectifCa = 1000L;
        //WHEN
        try {
            employeService.calculPerformanceCommercial(employe.getMatricule(),caTraite, objectifCa);
            Assertions.fail("La méthode calculPerformanceCommercial doit lancer une exception");
        } catch (EmployeException e) {
            //THEN
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule ne peut être null et doit commencer par un C !");

        }
    }


    @ParameterizedTest
    @CsvSource({
            "2, 600, 1000, 2", //Cas 1 / Autres
            "4, 10, 100, 2", //Cas 2
            "3, 1000, 1000, 4", //Cas 3
            "4, 720, 600, 6", //Cas 4
            "5, 10000, 600, 10", //Cas 5
    })
    void testCalculPerformanceCommerciale(Integer performanceInitiale, Long caTraite, Long objectifCa,  Integer performanceAttendue) throws EmployeException {

        //GIVEN
        Employe employe;
        String matricule  = "C06432";

        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(
                employe = new Employe("Doe", "Joe", matricule, LocalDate.now(), 160d, performanceInitiale, 1.0)
        );

        //WHEN
        employeService.calculPerformanceCommercial(employe.getMatricule(),caTraite, objectifCa);
        Integer performanceRecupere = employe.getPerformance();

        //THEN
        org.assertj.core.api.Assertions.assertThat(performanceRecupere).isEqualTo(performanceAttendue);
    }
}