package com.ipiecoles.java.java350.service;


import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

  // les mocks : pour pouvoir tester de manière unitaire une méthode qui a des dépendances.
  // Ici, on testera sans les dépendances des méthodes lastMatricule, findByMatricule, etc
  @InjectMocks
  private EmployeService employeService;

  @Mock
  private EmployeRepository employeRepository;

  @Test
  public void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException {
      //Given
      String nom = "Doe";
      String prenom = "John";
      Poste poste = Poste.TECHNICIEN;
      NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
      Double tempsPartiel = 1.0;
      Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
      Mockito.when((employeRepository.findByMatricule("T00001"))).thenReturn(null);

      //When
      employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

      //Then
      ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
      Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
      Employe e = employeArgumentCaptor.getValue();

      Assertions.assertEquals("T00001",e.getMatricule());
      Assertions.assertEquals(nom,e.getNom());
      Assertions.assertEquals(prenom, e.getPrenom());
      Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
      Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
      Assertions.assertEquals(1825.46, (double)e.getSalaire());
      Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
  }

  @Test
  public void testEmbaucheEmployeManagerMiTempsMasterLastMatricule00345() throws EmployeException {

      //Given
      String nom = "Spacey";
      String prenom = "Kevin";
      Poste poste = Poste.MANAGER;
      NiveauEtude niveauEtude = NiveauEtude.MASTER;
      Double tempsPartiel = 0.5;
      Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
      Mockito.when((employeRepository.findByMatricule("M00346"))).thenReturn(null);
      Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

      //When
      Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

      //Then
      Assertions.assertEquals("M00346",e.getMatricule());
      Assertions.assertEquals(nom,e.getNom());
      Assertions.assertEquals(prenom, e.getPrenom());
      Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
      Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
      Assertions.assertEquals(1064.85, (double)e.getSalaire());
      Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
  }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterLastMatricule99999() {

        //Given
        String nom = "Spacey";
        String prenom = "Kevin";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        try {
            Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            Assertions.assertEquals("Limite des 100000 matricules atteinte !", e1.getMessage());
        }

        //Then

    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterLastMatriculeExist() /*throws EmployeException*/{

        //Given
        String nom = "Spacey";
        String prenom = "Kevin";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());

        //When
        EntityExistsException e = Assertions.assertThrows(EntityExistsException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD", e.getMessage());

        // ou avec try catch

        /*try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("Aurait du planter");
        } catch (EntityExistsException e){
            Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD", e.getMessage());
        }*/

        //Then

    }

    @Test
    public void testCalculPerformanceCommercialMoins20() throws EmployeException {

        //Given
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(4);
        Long caTraite = 5000L;
        Long objectifCa = 10000L;
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(e);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        //When
        employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Integer performanceEmploye = employeArgumentCaptor.getValue().getPerformance();

        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, performanceEmploye);

    }

    @Test
    public void testCalculPerformanceCommercialEntreMoins20EtMoins5() throws EmployeException{
        //Given
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(4);
        Long caTraite = 8500L;
        Long objectifCa = 10000L;
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(e);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        //When
        employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Integer performanceEmploye = employeArgumentCaptor.getValue().getPerformance();

        Assertions.assertEquals((Integer)2, performanceEmploye);
    }

    @Test
    public void testCalculPerformanceCommercialEntreMoins5EtPlus5() throws EmployeException{
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(4);
        Long caTraite = 10000L;
        Long objectifCa = 10000L;
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(e);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        //When
        employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Integer performanceEmploye = employeArgumentCaptor.getValue().getPerformance();

        Assertions.assertEquals((Integer)4, performanceEmploye);
    }

    @Test
    public void testCalculPerformanceCommercialEntrePlus5EtPlus20() throws EmployeException{
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(4);
        Long caTraite = 11500L;
        Long objectifCa = 10000L;
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(e);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        //When
        employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Integer performanceEmploye = employeArgumentCaptor.getValue().getPerformance();

        Assertions.assertEquals((Integer)5, performanceEmploye);
    }

    @Test
    public void testCalculPerformanceCommercialPlus20() throws EmployeException{
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(4);
        Long caTraite = 15000L;
        Long objectifCa = 10000L;
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(e);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        //When
        employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Integer performanceEmploye = employeArgumentCaptor.getValue().getPerformance();

        Assertions.assertEquals((Integer)8, performanceEmploye);
    }

    @Test
    public void testCalculPerformanceCommercialPlus20EtSupMoyennePerf() throws EmployeException{
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(7);
        Long caTraite = 15000L;
        Long objectifCa = 10000L;
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(e);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        //When
        employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Integer performanceEmploye = employeArgumentCaptor.getValue().getPerformance();

        Assertions.assertEquals((Integer)12, performanceEmploye);
    }

    @Test
    public void testCalculPerformanceCommercialCaNull(){
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(2);
        Long caTraite = null;
        Long objectifCa = 10000L;

        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa));
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !", employeException.getMessage());
    }

    @Test
    public void testCalculPerformanceCommercialObjectifCaNegatif(){
        Employe e = new Employe();
        e.setMatricule("C12345");
        e.setPerformance(2);
        Long caTraite = 12000L;
        Long objectifCa = -10000L;

        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa));
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !", employeException.getMessage());
    }

    @Test
    public void testCalculPerformanceCommercialMatriculeErreur(){
        Employe e = new Employe();
        e.setMatricule("M12345");
        e.setPerformance(2);
        Long caTraite = 12000L;
        Long objectifCa = 10000L;

        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa));
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !", employeException.getMessage());
    }

    @Test
    public void testCalculPerformanceCommercialEmployeNonTrouve(){
        Long caTraite = 12000L;
        Long objectifCa = 10000L;

        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial("C12345", caTraite, objectifCa));
        Assertions.assertEquals("Le matricule C12345 n'existe pas !", employeException.getMessage());
    }

}
