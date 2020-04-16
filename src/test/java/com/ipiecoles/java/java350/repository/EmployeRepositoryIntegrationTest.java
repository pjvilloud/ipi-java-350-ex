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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryIntegrationTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    //Test nominal
    @Test
    public void testAvgPerformanceStartC(){
        //Given
        Employe emp1 = new Employe();
        emp1.setMatricule("C12345");
        emp1.setPerformance(1);
        employeRepository.save(emp1);

        Employe emp2 = new Employe();
        emp2.setMatricule("C67890");
        emp2.setPerformance(6);
        employeRepository.save(emp2);

        Employe emp3 = new Employe();
        emp3.setMatricule("C96587");
        emp3.setPerformance(5);
        employeRepository.save(emp3);

        //When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertEquals(4D, avg);
    }
}
