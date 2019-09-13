package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
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
    
    //Test vérifiant le chiffre d'affaire correspond à ce qu'on veut
    @Test
    public void testCalculPerformanceCommercialCaTraiteNull() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = null;
        Long objectifCa = 1000L;
        //When//Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa));
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !", e.getMessage());
    }
    
    @Test
    public void testCalculPerformanceCommercialCaTraiteNegatif() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = -100L;
        Long objectifCa = 1000L;
        //When//Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa));
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !", e.getMessage());
    }
    
    
    //Test vérifiant l'ojectif du chiffre d'affaire correspond à ce qu'on veut
    @Test
    public void testCalculPerformanceCommercialObjectifCaNull() throws EmployeException {
    	//Given
    	String matricule = "C00001";
        Long caTraite = 2000L;
        Long objectifCa = null;
    	//When//Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa));
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !", e.getMessage());
    }
    
    @Test
    public void testCalculPerformanceCommercialObjectifCaNegatif() throws EmployeException {
    	//Given
    	String matricule = "C00001";
        Long caTraite = 1000L;
        Long objectifCa = -100L;
    	//When//Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !", e.getMessage());
    }
    
    
    //Test vérifiant si le matricule correspond bien à ce que l'on veut
    @Test
    public void testCalculPerformanceCommercialMatriculeNull() throws EmployeException {
    	//Given
    	String matricule = null;
        Long caTraite = 100L;
        Long objectifCa = 100L;
        
    	//When//Then    	
    	EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
    	Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !", e.getMessage());
    }
    
    @Test
    public void testCalculPerformanceCommercialMatriculeDiffC() throws EmployeException {
    	//Given
    	String matricule = "M00001";
        Long caTraite = 100L;
        Long objectifCa = 100L;
        
    	//When//Then    	
    	EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
    	Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !", e.getMessage());
    }
    
    
    //Test vérifiant si le matricule se trouve dans la base
    @Test
    public void testCalculPerformanceCommercialMatriculeBDD() throws EmployeException {
    	//Given
    	String matricule = "C98765";
        Long caTraite = 100L;
        Long objectifCa = 100L;
        
    	//When//Then    	
    	EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
    	Assertions.assertEquals("Le matricule " + matricule + " n'existe pas !", e.getMessage());
    }
        
    /*
    *	Test des performances
    */   
    //1 : Si le chiffre d'affaire est inférieur de plus de 20% à l'objectif fixé, le commercial retombe à la performance de base  
    @Test
    public void testCalculPerformanceCommercialCAInf20ObjectifCA() throws EmployeException{
    	//Given
    	String nom = "Doe";
        String prenom = "John";
        String matricule = "C00001";
        Integer performance = 1;
        Double tempsPartiel = 1.0;
        Long caTraite = 150L;
        Long objectifCa = 150L;
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performance, tempsPartiel));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
    	
        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        
    	//Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());
        Assertions.assertEquals(1, (int)performanceArgumentCaptor.getValue().getPerformance());
    }
    
    //2 : Si le chiffre d'affaire est inférieur entre 20% et 5% par rapport à l'ojectif fixé, il perd 2 de performance (dans la limite de la performance de base)
    @Test
    public void testCalculPerformanceCommercialCAEntreMoins20Et5() throws EmployeException {
        //Given
    	String nom = "Doe";
        String prenom = "John";
        String matricule = "C00001";
        Integer performance = 2;
        Double tempsPartiel = 1.0;
        Long caTraite = 150L;
        Long objectifCa = 150L;
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe(prenom, nom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performance, tempsPartiel));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());
        Assertions.assertEquals(3, (int)performanceArgumentCaptor.getValue().getPerformance());
    }

    //3 : Si le chiffre d'affaire est entre -5% et +5% de l'objectif fixé, la performance reste la même.
    @Test
    public void testCalculPerformanceCommercialCAEntreMoins5EtPlus5() throws EmployeException {
        //Given
    	String nom = "Doe";
        String prenom = "John";
        String matricule = "C00001";
        Integer performance = 4;
        Double tempsPartiel = 1.0;
        Long caTraite = 120L;
        Long objectifCa = 150L;
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performance, tempsPartiel));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());
        Assertions.assertEquals(3, (int)performanceArgumentCaptor.getValue().getPerformance());
    }
    
 	//4 : Si le chiffre d'affaire est supérieur entre 5 et 20%, il gagne 1 de performance
    @Test
    public void testCalculPerformanceCommercialCAEntre5Et20() throws EmployeException {
        //Given
    	String nom = "Doe";
        String prenom = "John";
        String matricule = "C00001";
        Integer performance = 3;
        Double tempsPartiel = 1.0;
        Long caTraite = 160L;
        Long objectifCa = 150L;
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performance, tempsPartiel));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> performanceArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(performanceArgumentCaptor.capture());
        Assertions.assertEquals(5, performanceArgumentCaptor.getValue().getPerformance().intValue());
    }

    //5 : Si le chiffre d'affaire est supérieur de plus de 20%, il gagne 4 de performance
    @Test
    public void testCalculPerformanceCommercialCASuppA20() throws EmployeException {
    	//Given
        String nom = "Doe";
        String prenom = "John";
        String matricule = "C00001";
        Integer performance = 1;
        Double tempsPartiel = 1.0;
        Long caTraite = 190L;
        Long objectifCa = 150L;
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performance, tempsPartiel));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //WHEN
        employeService.calculPerformanceCommercial("C00001", caTraite, objectifCa);

        // THEN
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());

        Assertions.assertEquals(6, employeArgumentCaptor.getValue().getPerformance().intValue());   
    }

    //6 : Si la performance ainsi calculée est supérieure à la moyenne des performances des commerciaux, il reçoit + 1 de performance.
    @Test
    public void testCalculPerformanceCommercialPerfSupMoyPerfCal() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        String matricule = "C00001";
        Integer performance = 5;
        Double tempsPartiel = 1.0;
        Long caTraite = 190L;
        Long objectifCa = 150L;
        when(employeRepository.findByMatricule(matricule)).thenReturn(new Employe(nom, prenom, matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performance, tempsPartiel));
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);

        //WHEN
        employeService.calculPerformanceCommercial("C00001", 190L, 150L);

        // THEN
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals(10, employeArgumentCaptor.getValue().getPerformance().intValue());
    }
}