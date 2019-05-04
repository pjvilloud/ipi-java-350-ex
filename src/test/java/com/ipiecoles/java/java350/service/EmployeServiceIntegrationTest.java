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
    public void testEmbaucheCommercialPleinTempsBac() throws EmployeException

    {

        //Given
        employeRepository.save(new Employe("Spacey", "Kevin", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BAC;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Employe e = employeRepository.findByMatricule("C12346");
        Assertions.assertNotNull(e);
        Assertions.assertEquals("C12346",e.getMatricule());
        Assertions.assertEquals(nom,e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
        Assertions.assertEquals(1673.34, (double)e.getSalaire());
        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());

    }

    @Test
    public void testCalculPerformanceCommercial() throws EmployeException {
        //Given
        employeRepository.save(new Employe("Portnoy", "Mike", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Labrie", "James", "C23456", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        employeRepository.save(new Employe("Rudess", "Jordan", "C34567", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Petrucci", "John", "C45678", LocalDate.now(),Entreprise.SALAIRE_BASE, 4, 1.0));

        //When
        employeService.calculPerformanceCommercial("C12345",11500L, 10000L);

        //Then
        Employe e = employeRepository.findByMatricule("C12345");
        Assertions.assertEquals((Integer) 2, e.getPerformance());
    }

}
