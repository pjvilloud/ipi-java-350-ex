package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class EmployeTest {
	
	@Test
	public void testAncienneteDateEmbaucheNMoins2() {
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(2);
	}
	
	
	@Test
	public void testAncienneteDateEmbaucheNPlus2() {
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	@Test
	public void testAncienneteDateEmbaucheNull() {
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	@Test
	public void testAncienneteDateEmbaucheAujourdhui() {
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now());
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	/*
	@ParameterizedTest(name = "prime manager {0} est valide : {1}")
	@CsvSource({
		"1, 'T12345', 0, 1.0, 1000.0",
		"1, 'T12345', 0, 0.5, 500.0",
		"1, 'M12345', 0, 1.0, 1700.0",
		"2, 'T12345', 0, 1.0, 2300.0"
	})
	public void testPrimeAnnuelle(Integer performance, String matricule, Integer nbAnneeAnciennete, Double tempsPartiel, double prime) {
		//Given
		Employe employe = new Employe();
		employe.setPerformance(performance);
		employe.setMatricule(matricule);
		employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
		employe.setTempsPartiel(tempsPartiel);
		
		//When
		Double primeCalculee = employe.getPrimeAnnuelle();
		
		//Then
		Assertions.assertThat(primeCalculee).isEqualTo(prime);
	}*/
}
