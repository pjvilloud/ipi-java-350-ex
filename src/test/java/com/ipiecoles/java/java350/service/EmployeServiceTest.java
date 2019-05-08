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

    @Test
    public void testCalculPerformanceCommercialInferieureA20() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = Long.valueOf(1100);
        Long objectifCa = Long.valueOf(16000);
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe("Charbonnet", "Jean", matricule, LocalDate.now(), 1600.0, 1, 1.0));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());

        //1100 est inférieur à plus de 20% de 16000, donc performance de base + 1 car supérieure à la performance moyenne des commerciaux
        Assertions.assertEquals(1, performanceArgumentCaptor.getValue().getPerformance().intValue());
    }

    @Test
    public void testCalculPerformanceCommercialEntre5Et20DeMoins() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = Long.valueOf(14000);
        Long objectifCa = Long.valueOf(16000);
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe("Charbonnet", "Jean", matricule, LocalDate.now(), 1700.0, 2, 1.0));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());

        //Performance de base
        Assertions.assertEquals(1, performanceArgumentCaptor.getValue().getPerformance().intValue());
    }

    @Test
    public void testCalculPerformanceCommercialEntreMoins5EtPlus5() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = Long.valueOf(11000);
        Long objectifCa = Long.valueOf(11000);
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe("Charbonnet", "Jean", matricule, LocalDate.now(), 1700.0, 3, 1.0));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());

        //Performance + 1 car supérieure à la performance moyenne des commerciaux
        Assertions.assertEquals(4, performanceArgumentCaptor.getValue().getPerformance().intValue());
    }

    @Test
    public void testCalculPerformanceCommercialEntre5Et20() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = Long.valueOf(1200);
        Long objectifCa = Long.valueOf(1100);
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe("Charbonnet", "Jean", matricule, LocalDate.now(), 1700.0, 1, 1.0));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());

        // 1 + 1 (car enre 5 et 20% de plus) + 1 (car supérieure à la performance moyenne des commerciaux) = 3
        Assertions.assertEquals(3, performanceArgumentCaptor.getValue().getPerformance().intValue());
    }

    @Test
    public void testCalculPerformanceCommercialSuperieureAA20() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = Long.valueOf(30000);
        Long objectifCa = Long.valueOf(1500);
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe("Charbonnet", "Jean", matricule, LocalDate.now(), 1700.0, 3, 1.0));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());

        // 3 + 4 + 1 (car supérieure à la performance moyenne des commerciaux)
        Assertions.assertEquals(8, performanceArgumentCaptor.getValue().getPerformance().intValue());
    }
}