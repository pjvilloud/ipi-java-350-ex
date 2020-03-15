package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@SpringBootTest
// @DataJpaTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	@AfterEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	@Test
	public void testFindLastMatricule() {
	
	//Given
	Employe employe1 = employeRepository.save(new Employe("Jack", "Jacjie", "M12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
	Employe employe2 = employeRepository.save(new Employe("Jack", "Jackie", "T02345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));	
	
	//When
	String lastMatricule = employeRepository.findLastMatricule();
	
	//Then
	Assertions.assertThat(lastMatricule).isEqualTo("12345");
	}
	
	@Test
	public void testAvgPerformanceWhereMatriculeStartsWith() {
	
	//Given
	Employe employe = employeRepository.save(new Employe("Jack", "Jacjie", "M12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
	Employe employe2 = employeRepository.save(new Employe("Jack", "Jackie", "T02345", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
	
	//When
	Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
	
	//Then
	Assertions.assertThat(avgPerformance).isEqualTo(1d);
	}
}
