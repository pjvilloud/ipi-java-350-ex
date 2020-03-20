package com.ipiecoles.java.java350.service;


import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//import org.assertj.core.api.Assertions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup() {
        employeRepository.deleteAll();
    }


    @Test
    @DisplayName("Integration Test Calcul Performance")

    public void integrationCalculPerformanceCommercial() throws EmployeException {

        //Given
        employeRepository.save(new Employe("Doe", "John",
                "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1,
                1.0));
        String nom = "Doe";
        String prenom = "John";
        String matricule = "C12345";
        Long caTraite = 70L;
        Long objectifCa = 100L;
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Integer performance = 1;

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Employe employe = employeRepository.findByMatricule("C12345");
        Assertions.assertNotNull(employe);
        Assertions.assertEquals(nom, employe.getNom());
        Assertions.assertEquals(prenom, employe.getPrenom());
        Assertions.assertEquals(performance, employe.getPerformance());

    }

    @Test
    @DisplayName("Integration Test Calcul Performance & Moyenne")
    public void testCalculPerformanceCommercialMoyenneInte() throws EmployeException {

        // given
        Employe employe = new Employe();
        employe.setNom("Doe");
        employe.setPrenom("John");
        employe.setMatricule("C12345");
        employe.setDateEmbauche(LocalDate.now());
        employe.setSalaire(1000D);
        employe.setPerformance(1);
        employe.setTempsPartiel(1.0);
        Long caTraite = 1000L;
        Long objectifCa = 1000L;
        employeRepository.save(employe);

        // when
        employeService.calculPerformanceCommercial(employe.getMatricule(), caTraite, objectifCa);
        Double moyenneSalaireCommercial = employeRepository.avgPerformanceWhereMatriculeStartsWith(
                "C");

        // then
        Assertions.assertEquals(employe.getPerformance().doubleValue(), (moyenneSalaireCommercial));

    }


    @Test
    @DisplayName("Integration Test embaucheEmploye")
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


    @ParameterizedTest
    @DisplayName("Integration Test Paramétré calcul Perf")
    @CsvSource({
            " 70, 100,  1",
            "90, 100,  8",
            " 99, 100,  10",
            " 110, 100, 12",
            " 1500, 100, 15"
    })
    public void integrationCalculPerformanceCommercial(Long caTraite,
                                                       Long objectifCa,
                                                       Integer expectedPerformance) throws EmployeException {
        //Given
        Integer performance = 10;
        employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(),
                Entreprise.SALAIRE_BASE, performance, 1.0));

        //When
        Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        System.out.println(performanceMoyenne);
        employeService.calculPerformanceCommercial("C12345", caTraite, objectifCa);

        //Then
        Employe employe = employeRepository.findByMatricule("C12345");
        Assertions.assertEquals(expectedPerformance, employe.getPerformance());
    }


}


