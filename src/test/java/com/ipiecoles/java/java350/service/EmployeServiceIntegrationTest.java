package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

//    @Test
//    void testCalculPerformanceCommercialCasNominal() throws EmployeException {
//        //Given
//        Employe employe = new Employe();
//        employe.setMatricule("C45678");
//        employe.setPerformance(10);
//        employeRepository.save(employe);
//
//        //When
//        employeService.calculPerformanceCommercial(employe.getMatricule(),799L, 1000L);
//
//        //Then
//        Employe employe1 = employeRepository.findByMatricule("C45678");
//        Assertions.assertThat(employe1.getPerformance()).isEqualTo(1);
//    }

}
