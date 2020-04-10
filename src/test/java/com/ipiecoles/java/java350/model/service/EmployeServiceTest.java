package com.ipiecoles.java.java350.model.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmployeXEmploye() throws EmployeException {
        //Given
        // Quand la méthode findByMatricule va être appelée, on veux qu'elle renvoie une valeur
        // comme si il y avait plusieurs employes , dont le matricule le plus élevé est C45678
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");
        Mockito.when(employeRepository.findByMatricule("C45679")).thenReturn(null);
        //Then
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //When
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C45679");
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 1
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }

    @Test
    void embaucheEmployeEmbauchePartiel() throws EmployeException {
        //Given
        // Quand la méthode findByMatricule va être appelée, on veux qu'elle renvoie une valeur
        // comme si il y avait plusieurs employes , dont le matricule le plus élevé est C45678
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");
        Mockito.when(employeRepository.findByMatricule("C45679")).thenReturn(null);
        //Then
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 0.5;
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //When
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C45679");
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 0.5 = 912.73
        Assertions.assertThat(employe.getSalaire()).isEqualTo(912.73);
    }

    @Test
    void embaucheEmployeEmployeExiste() {
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("11111");
        Mockito.when(employeRepository.findByMatricule("C11112")).thenReturn(new Employe());

        Assertions.assertThatThrownBy(() ->
                employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel)
        )
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("L'employé de matricule C11112 existe déjà en BDD");
    }

    @Test
    void embaucheEmployeLimiteEmploye() {
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        Assertions.assertThatThrownBy(() ->
                employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("Limite des 100000 matricules atteinte !");
    }

    @Test
    void calculPerformanceCommercialPerfBaseTest() throws EmployeException {
        //Given
        String matricule = "C00001";
        Long caTraite = 0L;
        Long objectifCa = 1000000L;
        Employe employe = new Employe();
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(2.0);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employeSave = employeArgumentCaptor.getValue();
        Assertions.assertThat(employeSave.getPerformance()).isEqualTo(1);
    }

    @ParameterizedTest()
    @CsvSource({
            "C00001,800,1000,8,10.0,6",
            "C00001,950,1000,8,10.0,8",
            "C00001,1050,1000,8,10.0,8",
            "C00001,1200,1000,1,10.0,2",
            "C00001,13,10,9,10.0,14",
            "C00001,1000,1000,5,5.0,5",
            "C00001,1000,1000,5,4.9,6",
            "C00001,0,0,5,5.1,5",
    })
    void calculPerformanceCommercialCasTest(String matricule, Long caTraite, Long objectifCa,Integer performance, Double avgPerformance ,Integer performanceCalc) throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(performance);
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(avgPerformance);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employeSave = employeArgumentCaptor.getValue();
        Assertions.assertThat(employeSave.getPerformance()).isEqualTo(performanceCalc);
    }

    @ParameterizedTest()
    @CsvSource({
            //Given
            "C00001,-800,1000,Le chiffre d'affaire traité ne peut être négatif ou null !",
            "C00001,500,-1,L'objectif de chiffre d'affaire ne peut être négatif ou null !",
            "E00001,5000,5000,Le matricule ne peut être null et doit commencer par un C !",
    })
    void calculPerformanceCommercialNullTest(String matricule, Long caTraite, Long objectifCa,String message) throws EmployeException {

        //When/Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage(message);
    }

    //Test des null
    @Test
    void calculPerformanceCommercialCANullTest() {
        //Given
        String matricule = "C00001";
        Long caTraite = null;
        Long objectifCa = 1000000L;

        //When/Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

    @Test
    void calculPerformanceCommercialOCANullTest() {
        //Given
        String matricule = "C00001";
        Long caTraite = 5000L;
        Long objectifCa = null;

        //When/Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }

    @Test
    void calculPerformanceCommercialMatriculeNullTest() {
        //Given
        String matricule = null;
        Long caTraite = 5000L;
        Long objectifCa = 5000L;

        //When/Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule ne peut être null et doit commencer par un C !");
    }

    @Test
    void calculPerformanceCommercialMatriculeNotExistTest(){
        //Given
        String matricule = "C00001";
        Long caTraite = 2000L;
        Long objectifCa = 1000L;
        Employe employe = new Employe();
        employe.setPerformance(5);
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When/Then
        Assertions.assertThatThrownBy(() ->
                employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule " + matricule + " n'existe pas !");
    }
}