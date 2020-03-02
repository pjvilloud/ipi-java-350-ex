package com.ipiecoles.java.java350.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) //Junit 5
@DataJpaTest
public class EmployeRepositoryTest {

        @Autowired
        EmployeRepository employeRepository;
        @Test
        public void testFindLastMatricule(){

            //Given

            //When
            String lastMatricule = employeRepository.findLastMatricule();

            //Then
            Assertions.assertThat(lastMatricule).isNull();
        }

}
