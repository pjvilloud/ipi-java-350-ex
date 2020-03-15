package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {
	
	@Autowired
    EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }
    
    @ParameterizedTest
    @CsvSource({
    	"500, 1000, 1",
    	"900, 1000, 1",
    	"1000, 1000, 3",
    	"1100, 1000, 5",
    	"2000, 1000, 8"
    })
    public void integrationCalculPerformanceCommercial(Long caTraite, Long objectifCa, Integer perfAttendue) throws EmployeException {
        //Given
    	int performance = 3;
        employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, performance, 1.0));

        //When
        employeService.calculPerformanceCommercial("C12345", caTraite, objectifCa);

        //Then
        Employe employe = employeRepository.findByMatricule("C12345");
        Assertions.assertThat(employe.getPerformance()).isEqualTo(perfAttendue);
    }

}
