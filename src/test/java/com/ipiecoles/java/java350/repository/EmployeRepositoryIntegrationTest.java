package com.ipiecoles.java.java350.repository;


import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.service.EmployeService;
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
public class EmployeRepositoryIntegrationTest {

    @Autowired
    private EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void integrationAvgPerformanceWhereMatriculeStartsWith() throws EmployeException {
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12341", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "John", "T12342", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Doe", "John", "C12343", LocalDate.now(), Entreprise.SALAIRE_BASE, 8, 1.0));

        //When

        Double avgPerf = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");

        //Then

        Assertions.assertEquals(2, avgPerf);
    }

}