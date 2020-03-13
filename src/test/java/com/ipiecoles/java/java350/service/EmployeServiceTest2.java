package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import io.cucumber.core.gherkin.Argument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

//Le mocking permet d'injecter des "fausses" dépendances pour permettre la création de tests unitaires sur une fonction.
//Par exemple ici, embaucheEmploye dépend de l'employe repository, donc on le Mock pour pouvoir le créer.

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest2 {

    @InjectMocks
    private EmployeService employeService;

    @Mock // N'appelle donc pas le véritable employé repository
    private EmployeRepository employeRepository;

    @Test
    public void testEmbaucheTechnicienBTS() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");

        //When
        employeService.embaucheEmploye(nom,prenom,poste,niveauEtude,tempsPartiel);
        //Then

        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getMatricule()).isNotNull();
        Assertions.assertThat(employe.getMatricule()).startsWith("T");
        //1521.22*1.2 = 1825.464
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }

}