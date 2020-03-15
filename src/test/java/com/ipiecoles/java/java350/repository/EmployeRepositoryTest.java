package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;


@SpringBootTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	@AfterEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	/*
    * Tests de la méthode findLastMatricule
    * en utilisant une base vide puis une base avec plusieurs employés
    */
	
	@Test
	public void testFindLastMatricule0Emp() {
		
		//Given
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isNull();
	}
	
	@Test
	public void testFindLastMatricule2Emp() {
		
		//Given
		Employe e1 = new Employe("Doe", "Jane", "M99999", LocalDate.now(), 1500d, 1, 1.0);
		employeRepository.save(e1);
		
		Employe e2 = new Employe("Doe", "John", "T99998", LocalDate.now(), 1500d, 1, 1.0);
		employeRepository.save(e2);
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isEqualTo("99999"); 
	}
	
   /*
    * Test paramétré de la méthode avgPerformanceWhereMatriculeStartsWith
    * en utilisant des matricules pour différents types de poste 
    */
	
    @ParameterizedTest
    @CsvSource({
    	"'C', 4.5",
    	"'M', 3.5",
    	"'T', 2.5"
    })
    public void testAvgPerformanceWhereMatriculeStartsWith(String premiereLettreMatricule, Double avgPerf){
       
    	//Given
    	employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "C67890", LocalDate.now(), Entreprise.SALAIRE_BASE, 6, 1.0));
        employeRepository.save(new Employe("Doe", "John", "M12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M67890", LocalDate.now(), Entreprise.SALAIRE_BASE, 5, 1.0));
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "T67890", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));

        //When
        Double avgPerfCalculee = employeRepository.avgPerformanceWhereMatriculeStartsWith(premiereLettreMatricule);

        //Then
        Assertions.assertThat(avgPerfCalculee).isEqualTo(avgPerf);
    } 

	

}
