package com.ipiecoles.java.java350;

import java.time.LocalDate;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;

public class EmployeTest {
	
	@Test
	public void testS1() {
		
		//GIVEN
		Employe employe= new Employe();
		employe.setDateEmbauche(LocalDate.now());
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		//THEN
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	@Test
	public void testS2() {
		
		//GIVEN
		
		//When
		
		//THEN
		
	}
	
	@ParameterizedTest()
	@CsvSource({
	        "'C12345', 1.0, 0, 1, 1000.0",
	        "'C12345', 0.5, 0, 1, 500.0",
	        "'M12345', 1.0, 0, 1, 1700.0",
	        "'C12345', 1.0, 0, 2, 2300.0",
	})
	void testPimeAnuelleCommercialPleinTempsPerfBase(String matricule, Double  tempsPartiel, Integer nbAnneeAnciente, Integer performance, Double primeCalculee) {
	    //Given, 
		Employe employe= new Employe();
		employe.setMatricule(matricule);
		employe.setTempsPartiel(tempsPartiel);
		employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciente));
		employe.setPerformance(performance);
		//When, 
		Double prime = employe.getPrimeAnnuelle();
		//Then
		Assertions.assertThat(prime).isEqualTo(primeCalculee);
	
	   
	}

}
