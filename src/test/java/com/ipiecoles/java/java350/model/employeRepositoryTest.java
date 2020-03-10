package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.ipiecoles.java.java350.repository.EmployeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class employeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;
    
    @BeforeEach
    @AfterAll
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
    	Employe e2 = employeRepository.save(new Employe("doe","john","T11111", LocalDate.now(), 1600d, 1, 1.1));
    	
    	//When
    	String lastMatricule = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("99999");
    }
    
}
