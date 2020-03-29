package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;

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
	    public void testFindByImmat()
	    {
	        //Given
	        //Employe v = employeRepository.save(new Employe("AA-123-BB"));
	        //When
	        String lastMatricule = employeRepository.findLastMatricule();
	        //Then
	        Assertions.assertNull(lastMatricule);
	    }
	    @Test
	    public void avgPerformanceWhereMatriculeStartsWith(String premiereLettreMatricule)
	    {
	        //Given
	    	employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));
	        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
	        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
	        //When//Then
	        Assertions.assertEquals(3, employeRepository.avgPerformanceWhereMatriculeStartsWith("C"));
	    }
	}
}
