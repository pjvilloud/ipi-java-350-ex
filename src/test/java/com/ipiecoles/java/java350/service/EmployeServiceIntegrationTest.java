package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.*;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

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
        Assertions.assertEquals(1825.464, employe.getSalaire().doubleValue());
    }

    @ParameterizedTest(name = "caTraite de {1} et un caObjectif de {2} la performance attendu est {5}")
    @CsvSource({
            "'C12345',      9000,       10000,          5,              3",
            "'C12345',      1000,       1000,           30,             30",
            "'C12345',      12000,      10000,          50,             52",
            "'C12345',      14000,      10000,          10,             15",
    })
    void testCalculPerformanceCommercial(String matricule, Long caTraite, Long objectifCa, Integer perf, Integer perfAttendu) throws EmployeException{
        //Given

        Employe employe = new Employe();
        employe.setNom("Doe");
        employe.setPrenom("Jhon");
        employe.setSalaire(2000.00);
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now());
        employe.setPerformance(perf);

        employeRepository.save(employe);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
        employe = this.employeRepository.findByMatricule(matricule);

        //Then
        Assertions.assertEquals(employe.getPerformance(), perfAttendu);
    }
}