package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryIntegrationTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWith() {
        // Given
        List<Employe> listEmploye = new ArrayList<Employe>();
        listEmploye.add(new Employe("nom", "prenom", "C00000", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        listEmploye.add(new Employe("nom", "prenom", "C11111", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        listEmploye.add(new Employe("nom", "prenom", "C22222", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));

        listEmploye.add(new Employe("nom", "prenom", "T00000", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        listEmploye.add(new Employe("nom", "prenom", "T11111", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));
        listEmploye.add(new Employe("nom", "prenom", "T22222", LocalDate.now(), Entreprise.SALAIRE_BASE, 8, 1.0));

        listEmploye.add(new Employe("nom", "prenom", "M00000", LocalDate.now(), Entreprise.SALAIRE_BASE, 10, 1.0));
        listEmploye.add(new Employe("nom", "prenom", "M11111", LocalDate.now(), Entreprise.SALAIRE_BASE, 100, 1.0));
        listEmploye.add(new Employe("nom", "prenom", "M22222", LocalDate.now(), Entreprise.SALAIRE_BASE, 1000, 1.0));

        employeRepository.saveAll(listEmploye);

        // When
        Double averagePerfTechnicien = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");
        Double averagePerfCommercial = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        Double averagePerfManager = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");

        // Then
        Assertions.assertEquals(4.67, new BigDecimal(Double.toString(averagePerfTechnicien)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        Assertions.assertEquals(2.0, new BigDecimal(Double.toString(averagePerfCommercial)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        Assertions.assertEquals(370.0, new BigDecimal(Double.toString(averagePerfManager)).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}