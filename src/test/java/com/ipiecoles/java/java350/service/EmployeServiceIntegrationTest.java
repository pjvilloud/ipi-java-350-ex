package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    void testCalculPerformanceCommercialCasNominal() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("C45678");
        employe.setPerformance(3);
        employeRepository.save(employe);

        //When
        employeService.calculPerformanceCommercial(employe.getMatricule(),1000L, 1300L);

        //Then
        Employe employe1 = employeRepository.findByMatricule("C45678");
        Assertions.assertThat(employe1.getPerformance()).isEqualTo(1);
    }

}
