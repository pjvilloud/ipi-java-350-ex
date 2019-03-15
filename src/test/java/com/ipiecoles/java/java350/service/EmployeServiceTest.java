package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest extends EmployeService {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    //Réinitialise le jeu de données
    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    void testEmbaucheEmployeTechnicienPleinTpsBts() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        when(employeRepository.save(any())).thenAnswer(returnsFirstArg());

        //When Junit 5
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Assertions.assertThat(e.getNom()).isEqualTo(nom);
        Assertions.assertThat(e.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(e.getMatricule()).isEqualTo("T00001");
        Assertions.assertThat(e.getPerformance()).isEqualTo(1);

        //Salaire de base * Coefficient (=1521.22 * 1.2)
        Assertions.assertThat(e.getSalaire()).isEqualTo(1825.46);

        Assertions.assertThat(e.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(e.getTempsPartiel()).isEqualTo(tempsPartiel);

    }

    /**
     * Dernier matricule pour un employé : 00345
     * Vérifier avec la requête select max(substring(matricule,2)) from Employe
     *
     */
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
        when(employeRepository.save(any())).thenAnswer(returnsFirstArg());

        //When Junit 5
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Assertions.assertThat(e.getNom()).isEqualTo(nom);
        Assertions.assertThat(e.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(e.getMatricule()).isEqualTo("M00346");
        Assertions.assertThat(e.getSalaire()).isEqualTo(1064.85); //1521.22 * 1.4 * 0.5
        Assertions.assertThat(e.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(e.getTempsPartiel()).isEqualTo(tempsPartiel);

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
        when(employeRepository.save(any())).thenAnswer(returnsFirstArg());

        //When
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Assertions.assertThat(e.getNom()).isEqualTo(nom);
        Assertions.assertThat(e.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(e.getTempsPartiel()).isEqualTo(0.5);
        Assertions.assertThat(e.getMatricule()).isEqualTo("M00001");
    }

}