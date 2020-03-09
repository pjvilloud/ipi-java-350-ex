package com.ipiecoles.java.java350.model;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.assertj.core.api.Assertions;

import com.ipiecoles.java.java350.model.Employe;

public class EmployeTest {

	@Test
	public void testGetAnneeEmbauche() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(2);

	}
	
	// Quand la date d'embauche est dans le futur, l'emp a 0 année d'ancienneté
	
	@Test
	public void testGetAnneeEmbaucheNplus2() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	// Quand la date d'embauche est la dtae du jour, l'emp a 0 année d'ancienneté
	
	@Test
	public void testGetAnneeEmbaucheAujourdhui() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now());
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	
	// Quand la date d'embauche est nulle, on considère que l'emp a 0 année d'ancienneté
	
	@Test
	public void testGetAnneeEmbaucheNull() {
		
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}

	/**
	 * TESTS PARAMETRES
	 */
	
	@ParameterizedTest
	@CsvSource({
		"'T12341', 0, 1, 1.0, 1000.00",
		"'T12342', 2, 1, 1.0, 1200.00",
		"'T12343', 2, 1, 0.5, 600.00",
		"'T12344', 0, 2, 1.0, 2300.00",
		"'M12341', 0, 1, 1.0, 1700.00",


	})
	public void testPrimeAnnuelle(String matricule, Integer nbAnneesAnciennete, Integer performance, Double tempsPartiel, Double prime) {
		
		//Given
		Employe employe = new Employe();
		employe.setMatricule(matricule);
		employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
		employe.setPerformance(performance);
		employe.setTempsPartiel(tempsPartiel);
		
		//When
		Double primeCalculee = employe.getPrimeAnnuelle();
		
		//Then		
		Assertions.assertThat(primeCalculee).isEqualTo(prime);

	}
	
	
	
}
