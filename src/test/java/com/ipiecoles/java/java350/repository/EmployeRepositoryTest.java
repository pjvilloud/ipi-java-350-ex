package com.ipiecoles.java.java350.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@SpringBootTest
public class EmployeRepositoryTest {

	@Autowired
	public EmployeRepository employeRepository;
	
	/*@AfterEach
	public void setup() {
		employeRepository.deleteAll();
	}*/
	
	@Test
	public void findLastMatriculTestTwoEmploye() {
		//Given
		Employe e1 = new Employe();
		Employe e2 = new Employe();
		e1.setMatricule("M99998");
		e2.setMatricule("M99999");
		employeRepository.save(e1);
		employeRepository.save(e2);
		
		//When
		String result = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(result).isEqualTo(e2.getMatricule().substring(1));
		
	}
	@Test
	public void findLastMatriculTestZeroEmploye() {
		//Given
		
		//When
		String result = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(result).isNull();;
		
	}
	
	/*@Test
	@ParameterizedTest
	@CsvSource({"M0001,1","M0002,2","M0003,3","M0004,2","M0005,2","T0001,1","T0002,2","T0003,3","T0004,2","T0005,2"})
	public void avgDumb(String matricule, Integer performance) {
		Employe e1 = new Employe();
		e1.setMatricule(matricule);
		e1.setPerformance(performance);
		employeRepository.save(e1);
		
	}*/
	
	/*@Test
	@ParameterizedTest
	@CsvSource({"M","T"})
	public void avgPerformanceWhereMatriculeStartsWithTest(String start) {
		//Given
		
		//When
		Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith(start);
				
		//Then
		Assertions.assertThat(result).isEqualTo(2d);
	}*/
	
}

