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
class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtudes = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //When
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);

        // Then
        Assertions.assertEquals("T00001", e.getMatricule());
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
        Assertions.assertEquals(1825.46, (double)e.getSalaire());
        // le calcul du salaire correspond à : 1521.22 * 1.2 * 1 = 1825.46
        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
    }

    @Test
    public void testEmbaucheManagerMiTempsLastMatricule00345() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtudes = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("M00346")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // When
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);

        //Then

        Assertions.assertEquals("M00346", e.getMatricule());
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());

        Assertions.assertEquals(1064.85, (double)e.getSalaire());
        // le calcul du salaire correspond à : 1521.22 * 1.4 * 0.5 = 1064.85

        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());

    }

    @Test
    public void testEmbaucheManagerMiTempsLastMatricule99999() {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtudes = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        // When
        try {
            Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            //Then
            Assertions.assertEquals("Limite des 100000 matricules atteinte !", e1.getMessage());
        }

    }

    @Test
    public void testEmbaucheManagerMiTempsLastMatriculeExisteDeja() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtudes = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());

        // When
        try {
            Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);
            Assertions.fail("Devrait lancer une exception");
        } catch (EntityExistsException e1) {
            //Then
            Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD",
                    e1.getMessage());
        }
    }


    @Test
    public void testEmbaucheEmployeTechnicienPleinTempsBtsArgumentCaptor() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtudes = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);
        // Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertEquals("T00001", employeCaptor.getValue().getMatricule());
        Assertions.assertEquals(nom, employeCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeCaptor.getValue().getPrenom());
        Assertions.assertEquals(LocalDate.now(), employeCaptor.getValue().getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, employeCaptor.getValue().getPerformance());
        Assertions.assertEquals(1825.46, (double)employeCaptor.getValue().getSalaire());
        Assertions.assertEquals(tempsPartiel, employeCaptor.getValue().getTempsPartiel());

    }

    @Test
    public void testEmbauchEmployeTempsPartielNull() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtudes = NiveauEtude.BTS_IUT;
        Double tempsPartiel = null;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);

        // When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel) ;
            Assertions.fail("Devrait lancer une exception");
            // Then
        } catch (NullPointerException e1) {
            Assertions.assertEquals("Le temps partiel ne peut être null",
                    e1.getMessage());
        }
    }

    @ParameterizedTest(name = "employé matricule {0} : perf initiale {1}, CA traité {2}, objectif CA : {3}, perf attendue : {4}")
    @CsvSource( {
            "'C12345', 1, 60000, 60000, 1",
            "'C12345', 0, 60000, 60000, 1",
            "'C12345', 0, 59500, 60000, 1",
            "'C12345', 5, 63000, 60000, 5",
            "'C12345', 2, 44000, 60000, 1",
            "'C12345', 2, 48000, 60000, 1",
            "'C12345', 6, 48000, 60000, 4",
            "'C12345', 2, 48000, 60000, 1",
            "'C12345', 2, 66000, 60000, 3",
            "'C12345', 2, 72000, 60000, 3",
            "'C12345', 2, 75000, 60000, 6",
            "'C12345', 11, 60000, 60000, 12",
            "'C12345', 6, 75000, 60000, 10",
            "'C12345', 7, 75000, 60000, 12"
    })
    public void testCalculPerformanceCommercialNominal(String matricule, Integer performanceInitiale, Long caTraite, Long objectifCA, Integer performance) throws EmployeException {
        // Given
        Mockito.when(employeRepository.findByMatricule(matricule))
                .thenReturn(new Employe(
                        "Doe",
                        "John",
                        matricule,
                        LocalDate.now(),
                        Entreprise.SALAIRE_BASE,
                        performanceInitiale,
                        1.0));
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        // When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);

        // Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeCaptor.capture());
        Assertions.assertEquals(performance, employeCaptor.getValue().getPerformance());
    }

    @Test
    public void addBonusPerformanceCommercialPerfNull() {
        // Given
        Integer performance = null;

        // When
        try {
            employeService.addBonusPerformanceCommercial(performance);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("La performance ne peut être = null pour appliquer un bonus !",
                    e1.getMessage());
        }
    }

    @Test
    public void addBonusPerformanceCommercialInferieurMoyenne() throws EmployeException {
        //Given
        Integer performance = 1;
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);
        //When
        Integer performanceAvecBonus = employeService.addBonusPerformanceCommercial(performance);
        //Then
        Assertions.assertEquals(1, (int)performanceAvecBonus);
    }

    @Test
    public void addBonusPerformanceCommercialSuppMoyenne() throws EmployeException {
        //Given
        Integer performance = 2;
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
        //When
        Integer performanceAvecBonus = employeService.addBonusPerformanceCommercial(performance);
        //Then
        Assertions.assertEquals(3, (int)performanceAvecBonus);
    }



    @Test
    public void testCalculPerformanceCommercialCaTraiteNull() {
        // Given
        String matricule = "C12345";
        Long caTraite = null;
        Long objectifCA = new Long(60000);

        // When
        try {
            employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !",
                    e1.getMessage());
        }

    }

    @Test
    public void testCalculPerformanceCommercialCaTraiteNegatif() {
        // Given
        String matricule = "C12345";
        Long caTraite = new Long(-2000);
        Long objectifCA = new Long(60000);

        // When
        try {
            employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !",
                    e1.getMessage());
        }

    }

    @Test
    public void testCalculPerformanceCommercialObjectifCANull() {
        // Given
        String matricule = "C12345";
        Long caTraite = new Long(60000);
        Long objectifCA = null;

        // When
        try {
            employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !",
                    e1.getMessage());
        }

    }
    @Test
    public void testCalculPerformanceCommercialObjectifCANegatif() {
        // Given
        String matricule = "C12345";
        Long caTraite = new Long(60000);
        Long objectifCA = new Long(-60000);

        // When
        try {
            employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !",
                    e1.getMessage());
        }

    }

    @Test
    public void testCalculPerformanceCommercialMatriculeNull() {
        // Given
        String matricule = null;
        Long caTraite = new Long(60000);
        Long objectifCA = new Long(60000);

        // When
        try {
            employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !",
                    e1.getMessage());
        }

    }
    @Test
    public void testCalculPerformanceCommercialMatriculeTechnicien() {
        // Given
        String matricule = "T12345";
        Long caTraite = new Long(60000);
        Long objectifCA = new Long(60000);

        // When
        try {
            employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !",
                    e1.getMessage());
        }

    }

    @Test
    public void testCalculPerformanceCommercialPasEnBase() {
        // Given
        String matricule = "C12345";
        Long caTraite = new Long(60000);
        Long objectifCA = new Long(60000);
        Mockito.when(employeRepository.findByMatricule(matricule))
                .thenReturn(null);

        // When
        try {
            employeService.calculPerformanceCommercial(matricule, caTraite, objectifCA);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            // Then
            Assertions.assertEquals("Le matricule " + matricule + " n'existe pas !",
                    e1.getMessage());
        }
    }

}

