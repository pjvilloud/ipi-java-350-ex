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
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    // Remplace la vrai implémentation du repo par un fictif de test :
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
    @ExtendWith(SpringExtension.class)
    void embaucheEmploye2() throws EmployeException {
        //Given
        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
        when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertEquals( "Doe", employe.getNom());
   }

    @ParameterizedTest
    @CsvSource({"'Delacour', 'Michel',, 1825.46"
    })
    void embaucheEmployeTest(String nom, String prenom, Double tempsPartiel, Double result) throws EmployeException {
        //Given
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Employe newEmploye = new Employe("Delacour", "Michel", "T00001", LocalDate.now(), 1825.46, 1, null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        // Simule le .save(nouvelEmploye);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(newEmploye);

        //Then
        Assertions.assertEquals(employeRepository.findByMatricule("T00001").getSalaire(), result);
    }

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

           Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);
       }

       //When
       EmployeException e = Assertions.assertThrows(EmployeException.class, () ->  employeService.calculPerformanceCommercial(matricule,caTraite , objectifCa));

       //Then
        Assertions.assertEquals(e.getMessage(), result);
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
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);

        if (matricule.equals("C00012")) {
            Mockito.when( employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(40D);
        } else if (matricule.equals("C00013")) {
            Mockito.when( employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(null);
        } else {
            Mockito.when( employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1D);
        }

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        Employe newEmploye = new Employe("Delacour", "Michel", "T00001", LocalDate.now(), 1825.46, 1, null);
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(newEmploye);

        //Then
        Assertions.assertEquals(employeRepository.findByMatricule(matricule).getPerformance(), result);
    }
}