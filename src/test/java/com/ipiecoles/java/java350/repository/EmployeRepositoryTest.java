package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootTest
public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @AfterEach
    @BeforeEach
    public void purge(){
        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatriculeWithoutEmploye(){
        //Given

        //When
        String matricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(matricule).isNull();
    }

    @Test
    public void testFindLastMatriculeWith1Employe(){
        //Given
        Employe employe = new Employe("Doe", "John", "M12345", LocalDate.now(), 2500d,1, 1.0);
        employeRepository.save(employe);

        //When
        String matricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(matricule).isEqualTo("12345");
    }

    @Test
    public void testFindLastMatriculeWith3Employes(){
        //Given
        Employe employe1 = new Employe("Doe", "John", "C12345", LocalDate.now(), 2500d,1, 1.0);
        Employe employe2 = new Employe("Doe", "Jane", "M12342", LocalDate.now(), 2500d,1, 1.0);
        Employe employe3 = new Employe("Doe", "Peter", "T12341", LocalDate.now(), 2500d,1, 1.0);
        employeRepository.saveAll(Arrays.asList(employe1, employe2, employe3));

        //When
        String matricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(matricule).isEqualTo("12345");
    }

}
