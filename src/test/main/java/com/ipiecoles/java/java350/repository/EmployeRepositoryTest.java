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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    // avant et apres chaque test :
    @BeforeEach
    @AfterEach
    void setUp(){
        employeRepository.deleteAll();
    }

    @Test
    void findLastMatricule() {
        // Given

        // When
        String lastMarticule = employeRepository.findLastMatricule();

        // Then
    }

    @Test
    void findLastMatricule2() {
        // Given
        Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1050d, 1, 1d);
        Employe e2 = new Employe("TEST", "aa", "T67891", LocalDate.now(), 1050d, 1, 1d);
        Employe e3 = new Employe("wo", "nom", "T54899", LocalDate.now(), 1050d, 1, 1d);

        employeRepository.save(e1);
        employeRepository.save(e3);
        employeRepository.save(e2);

        // When
        String lastMarticule = employeRepository.findLastMatricule();

        // Then
        Assertions.assertEquals(lastMarticule, "67891");
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWithTest(){
        // Given
        Employe e = new Employe("wo", "nom", "M54899", LocalDate.now(), 1050d, 1, 1d);
        Employe e1 = new Employe("wo", "nom", "M54899", LocalDate.now(), 1050d, 2, 1d);
        Employe e2 = new Employe("wo", "nom", "M54899", LocalDate.now(), 1050d, 4, 1d);

        employeRepository.save(e);
        employeRepository.save(e1);
        employeRepository.save(e2);
        String premiereLettreMattricule = "M";

        // When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith(premiereLettreMattricule);

        // Then
        Assertions.assertEquals(avg,2.3333333333333335d);
    }
}