package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.ipiecoles.java.java350.repository.EmployeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class employeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;
    @Test
    public void testFindByMatricule(){
        //Given
        Employe e = new Employe("John", "Doe", "T12345", LocalDate.now(), 1200.0, 1, 1.0);
        employeRepository.save(e);
        //When
        Employe result = employeRepository.findByMatricule("T12345");
        //Then
        Assertions.assertThat(result).isEqualTo("T12345"); 
    }
}
