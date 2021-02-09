package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    //Problème, les services ont des dépendances extérieurs,
    // on fait donc des mocks pour simuler le fonctionnement des dépendances


    //On va utiliser des Mocks pour simuler le comportement de la classe repository
    //On va écrire des TU avec des Mocks

    @InjectMocks // pour dire que tous les mock vont être injecté ici
    private EmployeService employeService;

    @Mock //ce qu'on veut mocker
    private EmployeRepository employeRepository;

    @Test //////////Un TU avec mock
    public void testEmbauchePremierEmploye() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //on simule qu'aucun employé dans la bdd en utilisant null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        //On simule la recherche par matricule qui ne renvoie pas de résultat
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        //==>on peut utiliser Mockito.anyString(), pour dire quelque soit la valeur à la place du String "T01"


        //When
        //la méthode sauvegarde
        Employe employe = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");

    }

    @Test //////////Un TU avec mock
    public void testEmbaucheEmployeSupplementaire() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        String employeExistant = "T00002";

        //on simule qu'aucun employé dans la bdd en utilisant null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00001");

        //On simule la recherche par matricule qui ne renvoie pas de résultat
        Mockito.when(employeRepository.findByMatricule("T00002")).thenReturn(null);
        //==>on peut utiliser Mockito.anyString(), pour dire quelque soit la valeur à la place du String "T01"


        //When
        //la méthode sauvegarde
        Employe employe = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00002");

    }

    @Test //////////Un TU avec mock
    public void testEmbaucheEmployeTempsPartiel() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 0.5;
        String employeExistant = "T00002";

        //on simule qu'aucun employé dans la bdd en utilisant null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00001");

        //On simule la recherche par matricule qui ne renvoie pas de résultat
        Mockito.when(employeRepository.findByMatricule("T00002")).thenReturn(null);
        //==>on peut utiliser Mockito.anyString(), pour dire quelque soit la valeur à la place du String "T01"


        //When
        //la méthode sauvegarde
        Employe employe = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(912.73);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(0.5);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00002");

    }
}

