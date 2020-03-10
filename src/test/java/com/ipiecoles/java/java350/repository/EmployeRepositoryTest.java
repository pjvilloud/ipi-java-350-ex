package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;

/*
 * Méthode dans laquelle on simule l'utilisation d'un base de données via Travis
 */

@SpringBootTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	@Test
	public void testFindLastMatricule0Emp() {
		
		//Given
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isNull();
	}
	
	@Test
	public void testFindLastMatricule2Emp() {
		
		//Given
		Employe e1 = new Employe("Doe", "Jane", "M99999", LocalDate.now(), 1500d, 1, 1.0);
		employeRepository.save(e1);
		
		Employe e2 = new Employe("Doe", "John", "T99998", LocalDate.now(), 1500d, 1, 1.0);
		employeRepository.save(e2);
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isEqualTo("99999"); 
	}
	

}
