package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
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
	
	@Test
    public void testFindLastMatricule(){
        //Given
        Employe e = employeRepository.save(new Employe("Christophe", "LIMAO", "T99995", LocalDate.now(), 2500.0, 5, 1.0));
        //When
        String result = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(result).isEqualTo("99995"); 
    }
	
	@Test
    public void testAvgPerformanceWhereMatriculeStartsWith(){
        //Given
        Employe e1 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00001", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e2 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00002", LocalDate.now(), 2500.0, 7, 1.0));
        Employe e3 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00003", LocalDate.now(), 2500.0, 9, 1.0));
        //When
        Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
        //Then
        Assertions.assertThat(result).isEqualTo(7); 
    }
	
}
