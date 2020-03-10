package com.ipiecoles.java.java350.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ipiecoles.java.java350.model.Employe;

public class EmployeRepositoryTest 
{
	@RunWith(SpringRunner.class) //Junit 4
	@ExtendWith(SpringExtension.class) //Junit 5 
	@DataJpaTest // ou @SpringBootTest
	public class VehiculeRepositoryTest
	{

	    @Autowired
	    EmployeRepository employeRepository;
	    @Test
	    public void testFindByImmat(){
	        //Given
	        //Employe v = employeRepository.save(new Employe("AA-123-BB"));
	        //When
	        String lastMatricule = employeRepository.findLastMatricule();
	        //Then
	        Assertions.assertThat(lastMatricule).isNull(); 
	    }
	}
}
