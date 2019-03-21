package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void integrationEmbaucheEmploye() throws Exception{
        //Given
        String nom = "Hen";
        String prenom = "Tai";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Employe employe = employeRepository.findByMatricule("T00001");
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1);

        //1521.22*1.2*1.0
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());

    }
}
