package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.model.Employe;

@ExtendWith(SpringExtension.class) //Junit 5 
@DataJpaTest // ou @SpringBootTest
public class EmployeRepositoryTest {

	    @Autowired
	    EmployeRepository employeRepository;
	    
		@Test
		public void testFindByImmatNull(){
		    //Given
		    
		    //When
		    String result = employeRepository.findLastMatricule();
		    
		    //Then
		    Assertions.assertThat(result).isNull(); 
	}
		
		
	    @Test
	    public void testFindByImmat(){
	        //Given
	    	Employe emp3 = employeRepository.save(new Employe("emp3", "emp3", "X55555", LocalDate.now().minusMonths(10), 2500.00, 3, 0.50));
	        Employe emp = employeRepository.save(new Employe("emp1", "emp1", "T99999", LocalDate.now(), 2500.00, 3, 1.00));
	        Employe emp2 = employeRepository.save(new Employe("emp2", "emp2", "A33333", LocalDate.now().minusMonths(8), 2500.00, 3, 1.00));
	        
	        //When
	        String result = employeRepository.findLastMatricule();
	        
	        //Then
	        Assertions.assertThat(result).isEqualTo("99999"); 
	    }

	
}

