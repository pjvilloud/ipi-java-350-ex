package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeServiceIntegrationTest {
    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeService employeService;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testIntegrationEmploye() throws EmployeException {
        employeRepository.save(new Employe("Doe", "John", "M00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 1d;

        employeService.embaucheEmploye(nom,prenom,poste,niveauEtude,tempsPartiel);
        //Then
        Employe employe = employeRepository.findByMatricule("M00002");
        org.assertj.core.api.Assertions.assertThat(employe.getMatricule()).isEqualTo("M00002");
        org.assertj.core.api.Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        org.assertj.core.api.Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        org.assertj.core.api.Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        org.assertj.core.api.Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        org.assertj.core.api.Assertions.assertThat(employe.getSalaire()).isEqualTo(2129.71);
    }
}
