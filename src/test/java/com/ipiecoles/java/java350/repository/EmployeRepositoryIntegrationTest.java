package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
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
public class EmployeRepositoryIntegrationTest {
    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup() {
        employeRepository.deleteAll();
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithIsNull() {
        // Given
        employeRepository.save(new Employe("nom", "prenom", "matricule", LocalDate.now(), 1500d, 1, 1d));

        // When
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        // Then
        Assertions.assertNull(avgPerformance);
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithIsSingle() {
        // Given
        employeRepository.save(new Employe("nom", "prenom", "T00001", LocalDate.now(), 1500d, 1, 1d));

        // When
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        // Then
        Assertions.assertEquals(1, avgPerformance);
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithIsMultiple() {
        // Given
        employeRepository.save(new Employe("nom", "prenom", "T00001", LocalDate.now(), 1500d, 1, 1d));
        employeRepository.save(new Employe("nom", "prenom", "M00001", LocalDate.now(), 1500d, 1, 1d));
        employeRepository.save(new Employe("nom", "prenom", "T00002", LocalDate.now(), 1200d, 4, 1d));

        // When
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        // Then
        Assertions.assertEquals(2.5d , avgPerformance);
    }

    @Test
    public void testGetNbRttEmployeAvecDeuxAnsAnciennete() {
        // Given
        Employe employe = new Employe("nom", "prenom", "matricule", LocalDate.now().minusYears(2), 1500d, 1, 1d);

        // When
        Integer nbRtt = employe.getNbRtt();

        // Then
        Assertions.assertEquals(8, nbRtt);
    }
}
