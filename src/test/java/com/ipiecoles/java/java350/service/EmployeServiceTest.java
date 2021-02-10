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

import javax.persistence.EntityExistsException;
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

        //on simule la présence d'un employé en retournant un matricule
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

        //on simule la présence d'un employé en retournant un matricule
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

    @Test //////////Un TU avec mock
    public void testEmbaucheEmployeTempsDeTravailNull() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = null;

        //on simule la présence d'un employé en retournant un matricule
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
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.464);
        Assertions.assertThat(employe.getTempsPartiel()).isNull();
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00002");

    }


    @Test //////////Un TU avec UNE EXCEPTION
    public void testEmbaucheEmployeLimiteMatricule() {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //on simule la bdd pleine (matricule le plus haut)
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("la méthode embaucheEmploye aurait du lever une exception");
        }catch (EmployeException e){
            //Then
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }
    }


    @Test //////////Un TU avec UNE EXCEPTION
    public void testEmbaucheEmployeExisteDeja() throws EmployeException {
        //Given Pas d'employés en base
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Employe employeExistant = new Employe("Doe", "Jane", "T00001", LocalDate.now(), 1500d, 1, 1.0);
        //Simuler qu'aucun employé n'est présent (ou du moins aucun matricule)
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        //Simuler que la recherche par matricule renvoie un employé (un employé a été embauché entre temps)
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(employeExistant);
        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("embaucheEmploye aurait dû lancer une exception");
        } catch (Exception e){
            //Then
            Assertions.assertThat(e).isInstanceOf(EntityExistsException.class); //on récupère toutes les exceptions
            Assertions.assertThat(e.getMessage()).isEqualTo("L'employé de matricule T00001 existe déjà en BDD");
        }
    }


}

