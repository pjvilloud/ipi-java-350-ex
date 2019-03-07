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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeRepositoryTest {
    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void before() {
        employeRepository.deleteAll();
    }

    @Test
    void testFindLastMatriculeEmpty() {
        //Given

        //When
        String lastmatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertNull(lastmatricule);
    }

    @Test
    void testFindLastMatriculeUnique() {
        //Given
        employeRepository.save(new Employe("Jean", "Michel", "M00025", LocalDate.now(), 1000D, 1, 1.0D));

        //When
        String lastmatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("00025", lastmatricule);
    }

    @Test
    void testFindLastMatriculeMultiple() {
        //Given
        employeRepository.save(new Employe("Jean", "Michel", "M00025", LocalDate.now(), 1000D, 1, 1.0D));
        employeRepository.save(new Employe("Martin", "Henry", "C02025", LocalDate.now(), 1000D, 1, 1.0D));
        employeRepository.save(new Employe("Barbouze", "Ramadan", "T00666", LocalDate.now(), 1000D, 1, 1.0D));

        //When
        String lastmatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("02025", lastmatricule);
    }
}