package com.ipiecoles.java.java350.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;

@SpringBootTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	@Test
    public void testFindLastMatriculeA(){
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
    public void testFindLastMatriculeZ(){
		//Given
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isNull();
	}

	
	@Test
	public void testAvgPerfMatriculeStartsWith() {
		
		//Given
		Employe e = new Employe();
		Employe e1 = new Employe();
		Employe e2 = new Employe();
		Employe e3 = new Employe();
		Employe e4 = new Employe();
		e.setMatricule("C11111");
		e1.setMatricule("T11112");
		e2.setMatricule("M11113");
		e3.setMatricule("T11114");
		e4.setMatricule("T11115");
		e.setPerformance(3);
		e1.setPerformance(2);
		e2.setPerformance(1);
		e3.setPerformance(1);
		e4.setPerformance(4);
		employeRepository.save(e);
		employeRepository.save(e1);
		employeRepository.save(e2);
		employeRepository.save(e3);
		employeRepository.save(e4);
		
		String premiereLettreMatricule = "T";
		
		
		//When
		Double perf = employeRepository.avgPerformanceWhereMatriculeStartsWith(premiereLettreMatricule);
		perf = Math.round(perf*100d)/100d;
		
		//Then
		Assertions.assertThat(perf).isEqualTo(2.33);
	}
}
