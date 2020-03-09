package com.ipiecoles.java.java350.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;

@SpringBootTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@Test
    public void testFindLastMatricule3Employes(){
		//Given
		Employe e = new Employe();
		e.setMatricule("M22345");
		employeRepository.save(e);
		Employe e1 = new Employe();
		e1.setMatricule("T12345");
		employeRepository.save(e1);
		Employe e2 = new Employe();
		e2.setMatricule("C82345");
		employeRepository.save(e2);
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		//Then
		Assertions.assertThat(lastMatricule).isEqualTo("82345");
	}
	
	@Test
    public void testFindLastMatricule0Employe(){
		//Civen
		//Wheb
		
		String lastMatricule = employeRepository.findLastMatricule();
		//<then
		Assertions.assertThat(lastMatricule).isNull();
	}

}
