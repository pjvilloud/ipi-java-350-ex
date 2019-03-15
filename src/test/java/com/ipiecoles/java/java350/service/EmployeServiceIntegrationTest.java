package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.apache.commons.lang.StringUtils.substring;

@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @Test
    public void integrationEmbaucheEmploye() throws EmployeException {
        //Given avec de vraies données d'entrée
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        Employe e = employeRepository.findByMatricule("T12345");

        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When avec appel de vraies méthodes de repository
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        Employe e2 = employeRepository.findByMatricule("T12346");

        //Then avec de vraies vérifications
        Assertions.assertThat(e.getNom()).isEqualTo(e2.getNom());
        Assertions.assertThat(e.getPrenom()).isEqualTo(e2.getPrenom());
        Assertions.assertThat(substring(e.getMatricule(), 2)).isLessThan(substring(e.getMatricule(), 2, 5).concat("6"));
        Assertions.assertThat(e.getDateEmbauche()).isEqualTo(e2.getDateEmbauche());
        Assertions.assertThat(e.getTempsPartiel()).isEqualTo(e2.getTempsPartiel());

    }
}
