package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class EmployeServiceTest {

    //Problème, les services ont des dépendances extérieurs,
    // on fait donc des mocks pour simuler le fonctionnement des dépendances


    //On va utiliser des Mocks pour simuler le comportement de la classe repository
    //On va écrire des TU avec des Mocks

    private EmployeService employeService;

    private EmployeRepository employeRepository;

    @Test
    public void testEmbauchePremierEmploye() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When
        //la méthode renvoie une exception donc on ajoute le throws à la méthode
        Employe employe = employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then

    }



}

