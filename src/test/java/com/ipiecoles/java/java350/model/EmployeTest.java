package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EmployeTest {
	
	//Employé dateEmbauche avec date 2 ans avant aujourd'hui =>
	//2 années d'ancienneté
	
	@Test
	public void testGetAncienneteDateEmbaucheNmoins2() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(2);
	}
	
	//Date dans le futur => 0
	
	@Test
	public void testGetAncienneteDateEmbaucheNplus2() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	//Date aujourd'hui => 0
	@Test
	public void testGetAncienneteDateEmbaucheNow() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now());
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	
	//Date d'embauche indéfinie => 0
	
	@Test
	public void testGetAncienneteDateEmbaucheNull() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	//Test paramétrés
	//matricule, performance, date d'embauche, temps partiel, prime
	
	@ParameterizedTest(name = "immat {0} est valide : {1}")
	@CsvSource({
	        
	        "'T00002', 1, 0, 1.0, 1000.0",
	        "'C00004', 1, 0, 0.5, 500.0",
	        "'M00006', 1, 0, 1.0, 1700.0",
	        "'T00002', 2, 0, 1.0, 2300.0"	        
		
	})
	public void testCalculPrimeAnnuelle(String matricule, Integer performance, Integer annee, Double tempsPartiel, Double prime) {
		
		//Given
		Employe employe = new Employe();
		employe.setMatricule(matricule);
		employe.setPerformance(performance);
		employe.setDateEmbauche(LocalDate.now().minusYears(annee));
		employe.setTempsPartiel(tempsPartiel);
		
		
		//When
		Double primeAnnee = employe.getPrimeAnnuelle();
		
		//Then
		Assertions.assertThat(primeAnnee).isEqualTo(prime);
		
	}
	
	
}
