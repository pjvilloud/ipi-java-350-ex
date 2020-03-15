package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
public class employeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;
    
    @BeforeEach
    @AfterEach
    public void setup() {
    	employeRepository.deleteAll();
    }
    
    
    @Test
    public void testFindLastMatricule(){
        //Given	
        
        //When
        String lastMatricule = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(lastMatricule).isNull(); 
    }
    
    @Test
    public void testFindLatMatricule() {
    	//Given
    	Employe e1 = employeRepository.save(new Employe("doe","john","M99999", LocalDate.now(), 1500d, 1, 1.0));
    	Employe e2 = employeRepository.save(new Employe("smith","johnny","T11111", LocalDate.now(), 1600d, 1, 1.1));
    	
    	//When
    	String lastMatricule = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("99999");
    }
    
    @Test
    public void avgPerformanceWhereMatriculeStartsWith() {
    	//Given
    	Employe e1 = employeRepository.save(new Employe("doe","john","C99999", LocalDate.now(), 1500d, 8, 1.0));
    	Employe e2 = employeRepository.save(new Employe("sam","smith","C11111", LocalDate.now(), 1600d, 10, 1.1));
    	
    	//When
    	Double avgPerformance = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
    	
        //Then
    	// Average performance should be (8+10)/2 => 9   	
        Assertions.assertThat(avgPerformance).isEqualTo(9d);
    }
    
}
