package com.ipiecoles.java.java350.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceTestIntegration {
	
	@Autowired
	private EmployeService employeService;
	
	@Autowired
	private EmployeRepository employeRepository;
	
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	@Test
	public void testCalculPerformanceCommercialIntegre() throws EmployeException {
		
		//Given
		
		String matricule = "C88889";
		Long caTraite = 15000l;
		Long objectifCa = 12500l;
		Employe e = new Employe();
		e.setMatricule(matricule);
		e.setPerformance(2);
		employeRepository.save(e);
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//Then
		Assertions.assertThat(employeRepository.findByMatricule(matricule).getPerformance()).isEqualTo(4);
	}
}



