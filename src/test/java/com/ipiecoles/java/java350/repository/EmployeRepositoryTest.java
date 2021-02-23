package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    void setUp(){
        employeRepository.deleteAll();
    }

    @Test
    void testFindLastMatricule0Employe(){
        //Given
        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    void testFindLastMatriculeUnEmploye(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("12345");
    }

    @Test
    void testFindLastMatriculeTroisEmployes(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), 2000d, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M33632", LocalDate.now(), 2000d, 1, 1.0));
        employeRepository.save(new Employe("Doe", "John", "C83458", LocalDate.now(), 2000d, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("83458");
    }

    @Test
    void testAvgPerformanceWhereMatriculeStartsWith(){
        //Given
        employeRepository.save(new Employe("Schrute", "Dwight", "C00001", LocalDate.now(), 1500d, 5, 1.0));
        employeRepository.save(new Employe("Malone", "Kevin", "C00002", LocalDate.now(), 1500d, 1, 1.0));
        employeRepository.save(new Employe("Beesly", "Pam", "C00003", LocalDate.now(), 1500d, 3, 1.0));

        //When
        Double performanceAvg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(performanceAvg).isEqualTo(3d);
    }
}