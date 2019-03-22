package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeRepositoryTest {
    @Autowired
    public EmployeRepository employeRepository;

    @Test
    public void testFindLastMatricule() {
        //Given I have at least an employe in my database
        employeRepository.save(new Employe(
                "Roger", "Rabbit", "C12345", LocalDate.now(), 1500.0, 1, 1.0
        ));

        //When I search for the last matricule
        String matricule = employeRepository.findLastMatricule();

        //Then I retrieve the matricule for that employe
        Assertions.assertEquals("12345", matricule);
    }
}
