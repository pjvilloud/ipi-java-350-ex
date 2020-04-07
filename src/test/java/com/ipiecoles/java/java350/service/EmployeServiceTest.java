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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeServiceTest {

//    @InjectMocks
    @Autowired
    EmployeService employeService;
    @InjectMocks
    EmployeService employeService2;

    // Remplace la vrai implémentation du repo par un fictif de test :
    @Autowired
    EmployeRepository employeRepository;
    @Mock
    EmployeRepository employeRepository2;

    @BeforeEach
    @AfterEach
    void setup() {
        employeRepository.deleteAll();
    }

    @Test
    public void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        when(employeRepository2.findLastMatricule()).thenReturn("00345");
        when(employeRepository2.findByMatricule("T00346")).thenReturn(null);
        //When
        employeService2.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository2, times(1)).save(employeArgumentCaptor.capture());
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
        when(employeRepository2.findLastMatricule()).thenReturn("00345");
        when(employeRepository2.findByMatricule("M00346")).thenReturn(null);
        //When
        employeService2.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository2, times(1)).save(employeArgumentCaptor.capture());
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
        when(employeRepository2.findLastMatricule()).thenReturn(null);
        when(employeRepository2.findByMatricule("M00001")).thenReturn(null);
        //When
        employeService2.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository2, times(1)).save(employeArgumentCaptor.capture());
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
        when(employeRepository2.findLastMatricule()).thenReturn(null);
        when(employeRepository2.findByMatricule("M00001")).thenReturn(new Employe());
        //When/Then
        EntityExistsException e = Assertions.assertThrows(EntityExistsException.class, () -> employeService2.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
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
        when(employeRepository2.findLastMatricule()).thenReturn("99999");
        //When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService2.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("Limite des 100000 matricules atteinte !", e.getMessage());
    }

    @Test
    @ExtendWith(SpringExtension.class)
    void embaucheEmploye() throws EmployeException {
        //Given
        // Quand la méthode findLast va etre appelée, on veut qu elle renvoie null pour simuler une base employé vude
        when(employeRepository2.findLastMatricule()).thenReturn(null);

        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
        when(employeRepository2.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService2.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
        employeService2.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.LICENCE, 1.0);
    }

    @Test
    @ExtendWith(SpringExtension.class)
    void embaucheEmploye2() throws EmployeException {
        //Given
        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
        when(employeRepository2.findByMatricule("M45679")).thenReturn(null);

        //When
        employeService2.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository2).save(employeArgumentCaptor.capture());
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

//    @Test
//    void calculPerformanceCommercialNormalTest() {
//
//        //Given
//        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
////       employeRepository.deleteAll();
//        Employe e1 = new Employe("Doe", "John", "C00011", LocalDate.now(), 1050d, 1, 1d);
//        employeRepository.save(e1);
//
////       Mockito.when(employeRepository.findByMatricule("C00011")).thenReturn(e1);
//
//        //When
//        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial("C00011",2000L , 2500L));
//
//        //Then
//        Assertions.assertEquals(e.getMessage(), "Le matricule C00012 n'existe pas !");
//    }




}