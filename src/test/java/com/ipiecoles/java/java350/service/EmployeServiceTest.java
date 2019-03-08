package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;
    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmployeTechnicienPleinTempsBts() throws EmployeException {
        //given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("12345");

        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        //then
        ArgumentCaptor<Employe> EmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(EmployeCaptor.capture());

        Assertions.assertEquals(EmployeCaptor.getValue().getNom(),nom);
        Assertions.assertEquals(EmployeCaptor.getValue().getPrenom(),prenom);
        Assertions.assertEquals(EmployeCaptor.getValue().getMatricule(),"T12346");
        Assertions.assertEquals(EmployeCaptor.getValue().getTempsPartiel(),tempsPartiel);
        Assertions.assertEquals(EmployeCaptor.getValue().getPerformance(), Entreprise.PERFORMANCE_BASE);
        Assertions.assertEquals(EmployeCaptor.getValue().getSalaire(), (Double)1825.46);
        Assertions.assertEquals(EmployeCaptor.getValue().getDateEmbauche(), LocalDate.now());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("M00346")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
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
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("M00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
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
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());

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
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("Limite des 100000 matricules atteinte !", e.getMessage());
    }

    //#region Evaluation

    @Test
    void calculPerformanceCommercialThrowsWhenAskingForNonCommercial() throws EmployeException {
        // Given
        String matricule = "T12345";
        Long caTraite = 1L;
        Long objectifCa = 1L;
        // When
        // Then
        assertThatThrownBy(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa))
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule ne peut être null et doit commencer par un C !");
    }

    @Test
    void calculPerformanceCommercialThrowsWithNegativCA() throws EmployeException {
        // Given
        String matricule = "T12345";
        Long caTraite = -1L;
        Long objectifCa = 1L;
        // When
        // Then
        assertThatThrownBy(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa))
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

    @Test
    void calculPerformanceCommercialThrowsWithNegativObjectifCA() throws EmployeException {
        // Given
        String matricule = "T12345";
        Long caTraite = 1L;
        Long objectifCa = -1L;
        // When
        // Then
        assertThatThrownBy(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa))
                .isInstanceOf(EmployeException.class)
                .hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }

    @Test
    void calculPerformanceCommercialThrowsWithUnknownEmploye() throws EmployeException {
        // Given
        String matricule = "C12345";
        Long caTraite = 1L;
        Long objectifCa = 1L;
        // When
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);
        // Then
        assertThatThrownBy(() -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa))
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule " + matricule + " n'existe pas !");
    }

    @Test
    void testCalculPerformanceCommercialeCas2() throws EmployeException {
        //Given
        Employe emp = new Employe("kevin", "bob", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0D);
        String matricule = "C00001";
        long ca = 1400;
        long obj = 1600;
        //When
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(emp);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1D);
        employeService.calculPerformanceCommercial(matricule,ca,obj);
        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeCaptor.capture());
        Assertions.assertEquals(1,  (int)employeCaptor.getValue().getPerformance());
    }

    @Test
    void testCalculPerformanceCommercialeCas3() throws EmployeException {
        //Given
        Employe emp = new Employe("kevin", "bob", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0D);
        String matricule = "C00001";
        long ca = 1600;
        long obj = 1600;
        //When
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(emp);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);
        employeService.calculPerformanceCommercial(matricule,ca,obj);
        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeCaptor.capture());
        Assertions.assertEquals(1,  (int)employeCaptor.getValue().getPerformance());
    }

    @Test
    void testCalculPerformanceCommercialeCas4() throws EmployeException {
        //Given
        Employe emp = new Employe("kevin", "bob", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0D);
        String matricule = "C00001";
        long ca = 1760;
        long obj = 1600;
        //When
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(emp);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);
        employeService.calculPerformanceCommercial(matricule,ca,obj);
        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeCaptor.capture());
        Assertions.assertEquals(2,  (int)employeCaptor.getValue().getPerformance());
    }

    @Test
    void testCalculPerformanceCommercialeCas5() throws EmployeException {
        //Given
        Employe emp = new Employe("kevin", "bob", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0D);
        String matricule = "C00001";
        long ca = 2080;
        long obj = 1600;
        //When
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(emp);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);
        employeService.calculPerformanceCommercial(matricule,ca,obj);
        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeCaptor.capture());
        Assertions.assertEquals(5,  (int)employeCaptor.getValue().getPerformance());
    }

    @Test
    void testCalculPerformanceCommercialePerfSuppMoyen() throws EmployeException {
        //Given
        Employe emp = new Employe("kevin", "bob", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0D);
        String matricule = "C00001";
        long ca = 1760;
        long obj = 1600;
        //When
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(emp);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5D);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
        employeService.calculPerformanceCommercial(matricule,ca,obj);
        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeCaptor.capture());
        Assertions.assertEquals(3,  (int)employeCaptor.getValue().getPerformance());
    }
    //#endregion
}