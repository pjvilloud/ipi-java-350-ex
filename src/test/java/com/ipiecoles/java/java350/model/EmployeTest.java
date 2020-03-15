package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.model.Employe;



public class EmployeTest {
	
	//Employé dateEmbauche avec date 2 ans avant aujourd'hui =>
	// 2 années ancienneté
	
	@Test
	public void testAncienneteDateEmbaucheMoins2() {
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(2);
	}

	//Date dans le futur =>
	@Test
	public void testAncienneteDateEmbauchePlus2() {
		//Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));
		
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	//Date aujourd'hui =>
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
	
	//Date d'embauche indéfinie =>
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
	
	@ParameterizedTest
	@CsvSource ({
		"1, 'T12345', 0, 1.0, 1000.0",
		"1, 'T12345', 0, 0.5, 500.0",
		"1, 'M12345', 0, 1.0, 1700.0",
		"2, 'T12345', 0, 1.0, 2300.0"
	})
	public void testPrimeAnnuelle(Integer performance, String matricule, Integer nbAnneesAnciennete, Double tempsPartiel, Double prime) {
		//Given
		Employe employe = new Employe();
		employe.setMatricule(matricule);
		employe.setPerformance(performance);
		employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
		employe.setTempsPartiel(tempsPartiel);
	
		//When
		Double primeCalculee = employe.getPrimeAnnuelle();
		
		//Then
		Assertions.assertThat(primeCalculee).isEqualTo(prime);
	}
	
	@Test
	public void testAugmenterSalaire() {
		//Given
		Employe employe = new Employe();
		employe.setSalaire(Entreprise.SALAIRE_BASE);
		
		//When
		employe.augmenterSalaire(0.5);
		
		//Then
		Assertions.assertThat(employe.getSalaire()).isEqualTo(2281.83);
	}
	
	@Test
	public void testAugmenterSalaireNull() {
		//Given
		Employe employe = new Employe();
		employe.setSalaire(null);
		
		//When
		employe.augmenterSalaire(1.0);
		
		//Then
		Assertions.assertThat(employe.getSalaire()).isEqualTo(null);
	}
	
	@Test
	public void testAugmenterSalairePoucentagePositif() {
		//Given
		Employe employe = new Employe();
		employe.setSalaire(Entreprise.SALAIRE_BASE);
		
		//When
		employe.augmenterSalaire(1.0);
		
		//Then
		Assertions.assertThat(employe.getSalaire()).isEqualTo(3042.44);
	}
	
	@Test
	public void testAugmenterSalairePourcentageNull() {
		//Given
		Employe employe = new Employe();
		employe.setSalaire(Entreprise.SALAIRE_BASE);
		
		//When
		employe.augmenterSalaire(0.0);
		
		//Then
		Assertions.assertThat(employe.getSalaire()).isEqualTo(1521.22);
	}
	
}
