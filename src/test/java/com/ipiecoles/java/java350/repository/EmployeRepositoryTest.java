package com.ipiecoles.java.java350.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
	
	@Test
	public void findLastMatriculTest() {
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
	
}
