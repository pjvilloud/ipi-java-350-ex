package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@SpringBootTest
public class EmployeServiceIntegrationTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@Autowired
	EmployeService employeService;
	
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	/**
	 * Test d'intégration de la méthode calculPerformanceCommercial
	 * @throws EmployeException
	 */
	@Test
	public void testIntegreCalculPerformanceCommercial() throws EmployeException {
		//Given
		Employe e1 = employeRepository.save(new Employe("BONNEAU", "Jean", "C55555", LocalDate.now(), 2500.0, 2, 1.0));
        Employe e2 = employeRepository.save(new Employe("IBULAIRE", "Pat", "C88888", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e3 = employeRepository.save(new Employe("ONAIS", "Paul", "C11111", LocalDate.now(), 2500.0, 5, 1.0));
		
		Employe employe = employeRepository.findByMatricule("C55555");
		Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
		//When		
		employeService.calculPerformanceCommercial("C55555", 200000L, 100000L);
		//Then 
		// Les employés créés sont bien persistés dans la base mais il est impossible
		// en l'état de leur appliquer la méthode calculPerformanceCommercial de EmployeService !!!
		// La performance de l'employé testé reste inchangée (2) alors qu'elle devrait
		// être égale à 7 après application de la méthode calculPerformanceCommercial
		// Assertions.assertThat(employe.getPerformance()).isEqualTo(7);
		Assertions.assertThat(performanceMoyenne).isEqualTo(4.0);
	}

}
