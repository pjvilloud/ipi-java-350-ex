package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


    @ParameterizedTest
    @ValueSource(strings = {"C00001","C00002","C00003","C00004", "C00005", "C00006"})
    public void testMockCalculPerformanceCommercial(String matricule) throws EmployeException {
        // given
        // a base median performance of 0.0, which means all will get the "bonus" of +1 in performance
        // when
        when(employeRepository.findByMatricule(matricule)).thenReturn(new Employe());
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
        switch (matricule) {
            case "C00001":
                // 1 : Si le chiffre d'affaire est inférieur de plus de 20% à l'objectif fixé, le commercial retombe à la performance de base
                employeService.calculPerformanceCommercial("C00001", 70L,100L);
                Assertions.assertEquals(1, employeRepository.findByMatricule("C00001").getPerformance());
                break;
            case "C00002":
                // 2 : Si le chiffre d'affaire est inférieur entre 20% et 5% par rapport à l'ojectif fixé, il perd 2 de performance (dans la limite de la performance de base)
                employeService.calculPerformanceCommercial("C00002", 85L,100L);
                Assertions.assertEquals(1, employeRepository.findByMatricule("C00002").getPerformance());
                break;
            case "C00003":
                // 3 : Si le chiffre d'affaire est entre -5% et +5% de l'objectif fixé, la performance reste la même.
                employeService.calculPerformanceCommercial("C00003", 100L,100L);
                Assertions.assertEquals(1, employeRepository.findByMatricule("C00003").getPerformance());
                break;
            case "C00004":
                // 4 : Si le chiffre d'affaire est supérieur entre 5 et 20%, il gagne 1 de performance, il bénéficie du bonus
                employeService.calculPerformanceCommercial("C00004", 110L,100L);
                Assertions.assertEquals(3, employeRepository.findByMatricule("C00004").getPerformance());
                break;
            case "C00005":
                // 5 : Si le chiffre d'affaire est supérieur de plus de 20%, il gagne 4 de performance, il bénéficie du bonus
                employeService.calculPerformanceCommercial("C00005", 140L,100L);
                Assertions.assertEquals(6, employeRepository.findByMatricule("C00005").getPerformance());
                break;
            case "C00006":
                // 6 : Si le chiffre d'affaire est supérieur de plus de 20%, il gagne 4 de performance,
                // mais est en dessous de la moyenne
                when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);
                employeService.calculPerformanceCommercial("C00006", 140L,100L);
                Assertions.assertEquals(5, employeRepository.findByMatricule("C00006").getPerformance());
                break;
        }
    }

    @Test
    public void testMockCalculPerformanceNotCommercial(){
        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () ->
                employeService.calculPerformanceCommercial("M00001", 140L,100L)
        );
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !", employeException.getMessage());
    }



}