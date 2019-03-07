package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @Before
    public void before() {
        employeRepository.deleteAll();
    }

    @Test
    void test() throws EmployeException {
        //Given
        String nom = "Obama";
        String prenom = "Captain";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        Employe e = employeRepository.findByMatricule("T00001");

        //Then
        Assertions.assertNotNull(e);
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
        Assertions.assertEquals(new Integer(0), e.getNombreAnneeAnciennete());
        Assertions.assertEquals(1825.46, e.getSalaire().doubleValue());
    }
}