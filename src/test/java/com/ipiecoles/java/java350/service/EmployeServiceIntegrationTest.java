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
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeServiceIntegrationTest {
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    private EmployeService employeService;

    @BeforeEach
    @AfterEach
    public void setup() {
        employeRepository.deleteAll();
    }

    @Test
    public void testEmbaucheCommercialPleinTempsLicenceAsFirstEmploye() {
        String nom = "Rabbit";
        String prenom = "Jessica";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;

        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        } catch (EmployeException ex) {
            Assertions.fail(ex.getMessage());
        }


        //Then
        try {
            Employe e = employeRepository.findByMatricule("C00001");

            Assertions.assertEquals("C00001", e.getMatricule());
            Assertions.assertEquals(nom, e.getNom());
            Assertions.assertEquals(prenom, e.getPrenom());
            Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
            Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
            Assertions.assertEquals(1825.46, (double) e.getSalaire());
            Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
        } catch (EntityNotFoundException ex) {
            Assertions.fail(ex.getMessage());
        }

    }
}
