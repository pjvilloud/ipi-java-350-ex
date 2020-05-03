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
import java.time.format.DateTimeFormatter;

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
        Assertions.assertEquals(1825.46, employe.getSalaire().doubleValue());
    }


//    Méthode calculant la performance d'un commercial en fonction de ses objectifs et du chiffre d'affaire traité dans l'année.
//    Cette performance lui est affectée et sauvegardée en BDD
//    1 : Si le chiffre d'affaire est inférieur de plus de 20% à l'objectif fixé, le commercial retombe à la performance de base
//    2 : Si le chiffre d'affaire est inférieur entre 20% et 5% par rapport à l'ojectif fixé, il perd 2 de performance (dans la limite de la performance de base)
//    3 : Si le chiffre d'affaire est entre -5% et +5% de l'objectif fixé, la performance reste la même.
//    4 : Si le chiffre d'affaire est supérieur entre 5 et 20%, il gagne 1 de performance
//    5 : Si le chiffre d'affaire est supérieur de plus de 20%, il gagne 4 de performance
//    Si la performance ainsi calculée est supérieure à la moyenne des performances des commerciaux, il reçoit + 1 de performance.

    @Test
    public void integrationCalculPerformanceCommercial() throws EmployeException{
        //Given
        employeRepository.save(new Employe("Doe0", "John0", "C12340", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));
        employeRepository.save(new Employe("Doe1", "John1", "C12341", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));

        //When/Then

        // < 80% et perf < avgPerf
        employeService.calculPerformanceCommercial("C12340", (long)79, (long)100);
        Employe employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(1, employeTest.getPerformance());

        // < 80% et perf > avgPerf
        Employe employeTest2 = employeRepository.findByMatricule("C12341");
        employeTest2.setPerformance(0);
        employeRepository.save(employeTest2);

        employeService.calculPerformanceCommercial("C12340", (long)79, (long)100);
        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(2, employeTest.getPerformance());

        // >= 80% et perf < avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(4);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)80, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(2, employeTest.getPerformance());

        // >= 80% et perf > avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(-1);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)80, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(3, employeTest.getPerformance());

        // < 95% et perf < avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(4);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)94, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(2, employeTest.getPerformance());

        // < 95% et perf > avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(-1);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)94, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(3, employeTest.getPerformance());

        // >= 95% et perf < avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(4);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)95, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(4, employeTest.getPerformance());

        // >= 95% et perf > avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(3);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)95, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(5, employeTest.getPerformance());

        // <= 105% et perf < avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(4);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)105, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(4, employeTest.getPerformance());

        // <= 105% et perf > avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(3);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)105, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(5, employeTest.getPerformance());

        // > 105% et perf < avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(8);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)106, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(5, employeTest.getPerformance());

        // > 105% et perf > avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(4);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)106, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(6, employeTest.getPerformance());

        // <= 120% et perf < avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(8);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)120, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(5, employeTest.getPerformance());

        // <= 120% et perf > avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(4);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)120, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(6, employeTest.getPerformance());

        // > 120% et perf < avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(12);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)121, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(8, employeTest.getPerformance());

        // > 120% et perf > avgPerf
        employeTest.setPerformance(4);
        employeRepository.save(employeTest);
        employeTest2.setPerformance(4);
        employeRepository.save(employeTest2);
        employeService.calculPerformanceCommercial("C12340", (long)121, (long)100);

        employeTest = employeRepository.findByMatricule("C12340");
        Assertions.assertEquals(9, employeTest.getPerformance());

    }

}