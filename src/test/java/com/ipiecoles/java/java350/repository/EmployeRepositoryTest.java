package com.ipiecoles.java.java350.repository;


import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;

@ExtendWith(SpringExtension.class) //Junit 5
@DataJpaTest
public class EmployeRepositoryTest {

        @Autowired
        EmployeRepository employeRepository;


        @BeforeEach // Junit 5
        public void before(){
            //Appelé avant chaque test
            employeRepository.deleteAll();
        }

        @Test
        public void testFindLastMatricule(){

            //Given

            //When
            String lastMatricule = employeRepository.findLastMatricule();

            //Then
            Assertions.assertThat(lastMatricule).isNull();
        }

        @Test
        public void testFindLastMatricule3Employes(){

            //Given
            Employe e1 = new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0);
            Employe e2 = new Employe("Doe", "Jane", "C45678", LocalDate.now(), 2500d, 2, 0.5);
            Employe e3 = new Employe("Doe", "Peter", "M34567", LocalDate.now(), 3500d, 1, 0.5);

            employeRepository.saveAll(Arrays.asList(e1,e2,e3));

            //When
            String lastMatricule = employeRepository.findLastMatricule();

            //Then
            Assertions.assertThat(lastMatricule).isEqualTo("45678");
        }

}
