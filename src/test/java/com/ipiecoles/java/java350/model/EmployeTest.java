package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.exception.EmployeException;


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
	
	/*
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
	}
	*/
	
	//c'est ici que mon travail commence
	@Test
	public void testAugmenterSalaireDe10Pourcent() throws EmployeException 
	{
		//Given
		Employe employe = new Employe();
		employe.setSalaire(1000d);
		
		//When
		employe.augmenterSalaire(0.1);
		Double salaireAugmente = employe.getSalaire();
		
		//Then
		Assertions.assertThat(salaireAugmente).isEqualTo(1100d);
	}
	
	@Test
	public void testAugmenterSalaireAvecUnPourcentageNegatif() throws EmployeException
	{
		//Given
		Employe employe = new Employe();
		employe.setSalaire(1000d);
		
		//When
		employe.augmenterSalaire(-0.1);
		Double salaireAugmente = employe.getSalaire();
		
		//Then
		Assertions.assertThat(salaireAugmente).isEqualTo(1000d);
	}
	
	@Test
	public void testAugmenterSalaireAvecSalaireNull() throws EmployeException
	{
		//Given
		Employe employe = new Employe();
		employe.setSalaire(null);
		
		//When
		employe.augmenterSalaire(0.1);
		Double salaireAugmente = employe.getSalaire();
		
		//Then
		Assertions.assertThat(salaireAugmente).isEqualTo(null);
	}
	
	
	@Test 
	public void testAugmenterSalaireEgalZero() throws EmployeException
	{
		//Given
		Employe employe = new Employe();
		employe.setSalaire(0d);
		
		//When
		employe.augmenterSalaire(0.1);
		Double salaireAugmente = employe.getSalaire();
		
		//Then
		Assertions.assertThat(salaireAugmente).isEqualTo(0d);
	}
	//Nous ne faisons pas de test pour traîter le cas du salaire négatif. Ce cas devant être traité par la méthode qui set le salaire
	
}
