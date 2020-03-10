package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.model.Employe;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	@AfterAll
	public void setup() {
		employeRepository.deleteAll();
	}
	
	@Test
    public void testFindLastMatricule0Employe(){
        //Given
        //When
        String result = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(result).isNull(); 
    }
	
	@Test
    public void testFindLastMatricule3Employes(){
        //Given
        Employe e1 = employeRepository.save(new Employe("Christophe", "LIMAO", "C99995", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e2 = employeRepository.save(new Employe("Christophe", "LIMAO", "C88888", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e3 = employeRepository.save(new Employe("Christophe", "LIMAO", "T77777", LocalDate.now(), 2500.0, 5, 1.0));
        //When
        String result2 = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(result2).isEqualTo("99995"); 
    }
	
	@Test
    public void testAvgPerformanceWhereMatriculeStartsWith(){
        //Given
        Employe e1 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00001", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e2 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00002", LocalDate.now(), 2500.0, 7, 1.0));
        Employe e3 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00003", LocalDate.now(), 2500.0, 9, 1.0));
        //When
        Double result3 = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
        //Then
        Assertions.assertThat(result3).isEqualTo(7); 
    }
	
}
