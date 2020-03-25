package com.ipiecoles.java.java350.repository;


import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryTest {

	@Autowired
	EmployeRepository employeRepository;
	
	//OR @AfterAll
	@BeforeEach
	public void setUp() {
		employeRepository.deleteAll();
	}
	
	@Test
	public void testFindByLastMatricule() {
		
		//Given
		Employe e = employeRepository.save(new Employe("xyz", "xyz2", "T00001", LocalDate.now(), 3000.00, 1, 0.5));
		Employe e2 = employeRepository.save(new Employe("abc", "abc2", "T00002", LocalDate.now(), 2000.00, 1, 1.0));
		
		
		//When
		String result = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(result).isEqualTo("00002");
	}
	

	@Test
	public void testAvgPerformanceWhereMatriculeStartsWith() {
		
		//Given
		Employe e3 = employeRepository.save(new Employe("mno", "mno2", "T00003", LocalDate.now(), 3000.00, 1, 0.5));
		Employe e4 = employeRepository.save(new Employe("rst", "rst2", "T00004", LocalDate.now(), 2000.00, 1, 1.0));
		Employe e5 = employeRepository.save(new Employe("pqr", "pqr2", "T00005", LocalDate.now(), 2000.00, 1, 1.0));
		
		//When
		Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");
		
		//Then
		Assertions.assertThat(result).isEqualTo(1.0);
	}
	
}
