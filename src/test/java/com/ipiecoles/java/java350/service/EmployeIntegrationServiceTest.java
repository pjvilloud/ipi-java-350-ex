package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.Employe;
import com.ipiecoles.java.java350.Entreprise;
import com.ipiecoles.java.java350.NiveauEtude;
import com.ipiecoles.java.java350.Poste;
import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class EmployeIntegrationServiceTest {

    @BeforeEach
    @AfterEach //Supprimer les données qui se trouve dans le repository, pour être sur que notre repository prendra en compte notre given
    public void setup(){
        employeRepository.deleteAll();
    }

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    EmployeService employeService;

    String lastMatricule = employeRepository.findLastMatricule();

    @Test
    public void integrationEmbaucheEmploye() throws EmployeException {
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Employe employe = employeRepository.findByMatricule("T12346");
        Assertions.assertNotNull(employe);
        Assertions.assertEquals(nom, employe.getNom());
        Assertions.assertEquals(prenom, employe.getPrenom());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employe.getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertEquals("T12346", employe.getMatricule());
        Assertions.assertEquals(1.0, employe.getTempsPartiel().doubleValue());

        //1521.22 * 1.2 * 1.0
        Assertions.assertEquals(1825.46, employe.getSalaire().doubleValue());
    }
}
