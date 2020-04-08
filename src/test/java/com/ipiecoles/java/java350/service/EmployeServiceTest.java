package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.exception.EmployeException;
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
    public void testCalculPerformanceCommercialMatriculeIsExist() {
        //Given
        String matricule = "C00001";
        Long caTraite = 40L;
        Long objectifCa = 13454L;
        try{
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("Aurait du lancer une exception");
        } catch(EmployeException e){
            //Then
            org.assertj.core.api.Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule C00001 n'existe pas !");
        }
    }


    @Test
    public void testCalculPerformanceCommercialMatriculeNull1() {
        //Given
        String matricule = null;
        Long caTraite = 30L;
        Long objectifCa = 15000L;

        employeRepository.findByMatricule(matricule);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
            //When
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
        })//Then
                .isInstanceOf(EmployeException.class).hasMessage("Le matricule ne peut être null et doit commencer par un C !");
    }
     @ParameterizedTest
    @CsvSource({"C00011",
            "C00012", })
    public void testCalculPerformanceCommercialMatriculeIsNull2(String matricule) throws EmployeException {
        //Given
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);
        //When
        EmployeException e = Assertions.assertThrows(EmployeException.class, () ->  employeService.calculPerformanceCommercial(matricule,2000L , 2500L));
        //Then
        Assertions.assertEquals(e.getMessage(), "Le matricule "+matricule+" n'existe pas !");
    }

    @ParameterizedTest
    @CsvSource({"C00011",
            "C00012", })
    public void testCalculPerformanceCommercialEmployeIsNull(String matricule) throws EmployeException {
        //Given
        Employe emp = employeRepository.findByMatricule(matricule);
        try {
            Mockito.when(emp).thenReturn(null);
            employeService.calculPerformanceCommercial(matricule, 2000L, 2500L);
            //When
        }catch (EmployeException e){
            //Then
            Assertions.assertEquals(e.getMessage(), "Le matricule "+matricule+" n'existe pas !");
        }
    }

    @Test
    public void testCalculPerformanceCommercialMatriculeStartC(){
        //Given
        String matricule = "M00001";
        Long caTraite = 50L;
        Long objectifCa = 20000L;
        //Given
        try{
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("Aurait du lancer une exception");
        } catch(EmployeException e){
            //Then
            org.assertj.core.api.Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule ne peut être null et doit commencer par un C !");
        }
    }

    @Test
    public void testCalculPerformanceCommercialCaTraiteNegatif(){
        //Given
        String matricule = "C00001";
        Long caTraite = -40L;
        Long objectifCa = 40000L;
        try{
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("Aurait du lancer une exception");
        } catch(EmployeException e){
            //Then
            org.assertj.core.api.Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !");
        }
    }
    @Test
    public void testCalculPerformanceCommercialCaTraiteNull(){
        //Given
        String matricule = "C00001";
        Long caTraite = null;
        Long objectifCa = 40000L;
        try{
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("Aurait du lancer une exception");
        } catch(EmployeException e){
            //Then
            org.assertj.core.api.Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !");
        }
    }
    @Test
    public void testCalculPerformanceCommercialObjectifCaNegatif(){
        //Given
        String matricule = "C00001";
        Long caTraite = 50L;
        Long objectifCa = -40000L;
        try{
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("Aurait du lancer une exception");
        } catch(EmployeException e){
            //Then
            org.assertj.core.api.Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !");
        }
    }
    @Test
    public void testCalculPerformanceCommercialObjectifCaNull(){
        //Given
        String matricule = "C00001";
        Long caTraite = 50L;
        Long objectifCa = null;
        try{
            employeService.calculPerformanceCommercial(matricule,caTraite,objectifCa);
            Assertions.fail("Aurait du lancer une exception");
        } catch(EmployeException e){
            //Then
            org.assertj.core.api.Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire ou l'objectif de chiffre d'affaire traités ne peuvent être négatifs ou null !");
        }
    }
}