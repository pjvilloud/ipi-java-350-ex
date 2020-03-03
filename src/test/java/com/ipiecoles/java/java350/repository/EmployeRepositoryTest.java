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

    @BeforeEach
    public void before(){
        employeRepository.deleteAll();
    }

    @Test
    void testFindLastMatricule(){

        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();

    }

    @Test
    void testFindLastMatricule3Employe(){

        //Given
        Employe e1 = new Employe("Doe","Johny","T12345", LocalDate.now(),1500d,1,1.0);
        Employe e2 = new Employe("Doe","Rene","C45678", LocalDate.now(),2000d,2,0.5);
        Employe e3 = new Employe("Doe","Paul","M34567", LocalDate.now(),3500d,1,0.5);

        employeRepository.saveAll(Arrays.asList(e1,e2,e3));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("45678");

    }
}