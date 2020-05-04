package com.ipiecoles.java.java350.repository;


import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    void testAvgPerformanceWhereMatriculeStartsWith_None_Succes(){
        // 0 - Arrange
        employeRepository.save(new Employe("nom", "prenom", "matricule", LocalDate.now(), 1500d, 1, 1d));

        // 1 - Act
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        // 2 - Assert
        Assertions.assertThat(avgPerformance).isEqualTo(null);
    }

    @Test
    void testAvgPerformanceWhereMatriculeStartsWith_Single_Succes(){
        // 0 - Arrange
        employeRepository.save(new Employe("nom", "prenom", "T00001", LocalDate.now(), 1500d, 1, 1d));

        // 1 - Act
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        // 2 - Assert
        Assertions.assertThat(avgPerformance).isEqualTo(1);
    }

    @Test
    void testAvgPerformanceWhereMatriculeStartsWith_Multiple_Succes(){
        // 0 - Arrange
        employeRepository.save(new Employe("nom", "prenom", "T00001", LocalDate.now(), 1500d, 1, 1d));
        employeRepository.save(new Employe("nom", "prenom", "M00001", LocalDate.now(), 1500d, 1, 1d));
        employeRepository.save(new Employe("nom", "prenom", "T00002", LocalDate.now(), 1200d, 4, 1d));

        // 1 - Act
        Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        // 2 - Assert
        Assertions.assertThat(avgPerformance).isEqualTo(2.5D);
    }
}
