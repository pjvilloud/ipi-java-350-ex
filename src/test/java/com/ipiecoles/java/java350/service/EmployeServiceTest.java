package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @BeforeEach // Junit 5
    public void setUp(){
        //Appelé avant chaque test
        MockitoAnnotations.initMocks(this.getClass());
    }

    @InjectMocks
    private EmployeService employeService;

    @Mock
     private EmployeRepository employeRepository;

    @Test
    public void testEmbaucheTechnicienBTSTempsPleinMatricule() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Mockito.verify(employeRepository).findByMatricule("T00346");
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);

        Mockito.verify(employeRepository).save(employeCaptor.capture());
        Employe employe = employeCaptor.getValue();

        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employeCaptor.getValue().getMatricule()).isEqualTo("T00346");
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);

    }

    @Test
    public void testEmbaucheTechnicienBTSTempsPleinLimiteMatricule() {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        Throwable exception = Assertions.catchThrowable(() ->
                employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));

        //Then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");

    }

    @Test
    public void testEmbaucheCommercialMitemps() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 0.5;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Mockito.verify(employeRepository).findByMatricule("C00001");
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);

        Mockito.verify(employeRepository).save(employeCaptor.capture());
        Employe employe = employeCaptor.getValue();

        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employeCaptor.getValue().getMatricule()).isEqualTo("C00001");
        Assertions.assertThat(employe.getSalaire()).isEqualTo(912.73d);

        }


    @Test
    public void testCalculPerformanceCommercialCaNull() throws EmployeException {

        //given
        String matricule = "T12345";
        Long caTraite = null;
        Long objCa = 100000L;

        //when
        Throwable exception = Assertions.catchThrowable(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objCa));

        //then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

    @Test
    public void testCalculPerformanceCommercialObjNull() throws EmployeException {

        //given
        String matricule = "T12345";
        Long caTraite = 100000L;
        Long objCa = null;

        //when
        Throwable exception = Assertions.catchThrowable(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objCa));

        //then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }

    @Test
    public void testCalculPerformanceCommercialMatriculeDifferentC() throws EmployeException {

        //given
        String matricule = "T12345";
        Long caTraite = 100000L;
        Long objCa = 100000L;

        //when
        Throwable exception = Assertions.catchThrowable(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objCa));

        //then
        Assertions.assertThat(exception).isInstanceOf(EmployeException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Le matricule ne peut être null et doit commencer par un C !");
    }
}
