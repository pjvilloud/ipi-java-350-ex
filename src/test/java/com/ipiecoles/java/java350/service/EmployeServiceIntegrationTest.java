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


@ExtendWith(SpringExtension.class) // pour charger un environnement
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
        //Given avec de vraies données d'entrées
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(),
                Entreprise.SALAIRE_BASE, 1, 1.0));

        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtudes = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When avec appel des vraies méthodes de repository...
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);

        //Then avec de vraies vérifications...
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
}
