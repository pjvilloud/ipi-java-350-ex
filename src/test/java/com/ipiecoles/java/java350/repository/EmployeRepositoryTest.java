package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;


@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach // Junit 5
    public void before(){//Nom before arbitraire
        //Appelé avant chaque test
        employeRepository.deleteAll();
    }

    @Test
    void testFindLastMatriculex() {
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    void testAvgPerformanceWhereMatriculeStartsWith() {
        //Given
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0);
        Employe employe2 = new Employe("Doe", "Jane", "C45678", LocalDate.now(), 2500d, 2, 0.5);
        Employe employe3 = new Employe("Doe", "Peter", "M34567", LocalDate.now(), 3500d, 1, 0.5);

        employeRepository.saveAll(Arrays.asList(employe, employe2, employe3));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("45678");
    }
}
