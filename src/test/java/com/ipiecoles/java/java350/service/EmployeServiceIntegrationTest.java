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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    public EmployeService employeService;

    @Autowired
    public EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup() {
        employeRepository.deleteAll();
    }

    @Test
    public void testIntegrationEmbaucheEmploye() throws EmployeException {
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(),
                Entreprise.SALAIRE_BASE, 1, 1.0));

        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtudes = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);

        //Then
        Employe employe = employeRepository.findByMatricule("T12346");

        Assertions.assertNotNull(employe);
        Assertions.assertEquals("T12346", employe.getMatricule());
        Assertions.assertEquals(nom, employe.getNom());
        Assertions.assertEquals(prenom, employe.getPrenom());
        Assertions.assertEquals(LocalDate.now(), employe.getDateEmbauche());
        Assertions.assertEquals(1825.46, employe.getSalaire().doubleValue());
        // le calcul du salaire correspond à : 1521.22 * 1.2 * 1 = 1825.46
        Assertions.assertEquals(tempsPartiel, employe.getTempsPartiel());
    }

    @Test
    public void testIntegrationCalculPerformanceCommercial() throws EmployeException {
        //Given
        employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(),
                Entreprise.SALAIRE_BASE, 10, 1.0));
        // When
        Employe e = employeRepository.findByMatricule("C12345");
        employeService.calculPerformanceCommercial(e.getMatricule(), new Long(75000), new Long(60000));

        // Then
        e = employeRepository.findByMatricule("C12345");
        // le calcul de la performance correspond à 10 + 4 + 1
        Assertions.assertEquals(15, (int)e.getPerformance());
    }
}
