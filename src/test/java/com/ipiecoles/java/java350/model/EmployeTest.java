package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.exception.EmployeException;

public class EmployeTest {

	// Employe dateEmb avec date 2ans avant aujourd'hui => 2 années d'anciennete
	@Test
	public void testAncienneteDateEmbaucheNmoins2() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));

		// When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnees).isEqualTo(2);

	}

	// Date dans le futur => 0
	@Test
	public void testAncienneteDateEmbaucheNplus2() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));

		// When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);

	}

	// Date aujourd'hui => 0
	@Test
	public void testAncienneteDateEmbaucheAujourdhui() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now());

		// When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);

	}

	// Date d'embauche indéfinie => 0
	@Test
	public void testAncienneteDateEmbaucheNull() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);

		// When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnees).isEqualTo(0);

	}

	// parameters: entres: matricule, nombreAnneeAnnciennete, performance,
	// tempsPartiel; resultat: prime
	@ParameterizedTest
	@CsvSource({ ", 0, 1, 1.0, 1000.0", "'M01234', 2, 1, 1.0, 1900.0", "'M01234', 2, 1, 0.7, 1330.0",
			"'T01234', 0, 1, 1.0, 1000.0", "'T01234', 0, 1, 0.5, 500.0", "'T01234', 2, , 1.0, 1200.0",
			"'T01234', 2, 2, 1.0, 2500.0",

	})
	public void testPrimeAnnuelle(String matricule, Integer nbAnneesAnciennete, Integer performance,
			Double tempsPartiel, Double prime) {
		// Given
		Employe employe = new Employe();
		employe.setMatricule(matricule);
		employe.setPerformance(performance);
		employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
		employe.setTempsPartiel(tempsPartiel);

		// When
		Double primeCalculee = employe.getPrimeAnnuelle();

		// Then
		Assertions.assertThat(primeCalculee).isEqualTo(prime);
	}
	
	// Tests augmenterSalaire

	// cas nominal
	@Test
	public void test01AugmenterSalaire2pourcent() throws EmployeException {

		// Given
		Employe emp = new Employe();
		emp.setSalaire(1350.0);

		// When
		emp.augmenterSalaire(0.02);

		// Then
		Assertions.assertThat(emp.getSalaire()).isEqualTo(1377.0);

	}

	// avec arrondi
	@Test
	public void test02AugmenterSalaireArrondi() throws EmployeException {

		// Given
		Employe emp = new Employe();
		emp.setSalaire(1352.12);

		// When
		emp.augmenterSalaire(0.017);

		// Then
		Assertions.assertThat(emp.getSalaire()).isEqualTo(1375.11);

	}

	// sans salaire definie
	@Test
	public void test03AugmenterSalaireParDefaut() throws EmployeException {

		// Given
		Employe emp = new Employe();

		// When
		emp.augmenterSalaire(0.12);

		// Then
		Assertions.assertThat(emp.getSalaire()).isEqualTo(1703.77);

	}

	// avec parametre de pourcentage incorrect
	@Test
	public void test04AugmenterSalairePourcentageIncorrect() throws EmployeException {

		// Given
		Employe emp = new Employe();

		// When
		try {
			emp.augmenterSalaire(0.5);
			Assertions.fail("Aurait dû planter !");
		} catch (Exception e) {
			// Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le pourcentage d'augmentation ne doit pas exceder 30%");
		}
	}
	
	// avec salaire null
	@Test
	public void test05AugmenterSalaireNull() throws EmployeException {

		// Given
		Employe emp = new Employe();
		emp.setSalaire(null);

		// When
		try {
			emp.augmenterSalaire(0.05);
			Assertions.fail("Aurait dû planter !");
		} catch (Exception e) {
			// Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("La valeur de salaire n'est pas définie !");
		}

	}

	@Test
	public void test06AugmenterSalairePourcentageNull() throws EmployeException {

		// Given
		Employe emp = new Employe();
		emp.setSalaire(1500.0);
		Double pourcentage = null;

		// When
		try {
			emp.augmenterSalaire(pourcentage);
			Assertions.fail("Aurait dû planter !");
		} catch (Exception e) {
			// Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le pourcentage ne doit pas être négatif ou null !");
		}
	}
	
	@Test
	public void test07AugmenterSalairePourcentageNegatif() throws EmployeException {

		// Given
		Employe emp = new Employe();
		emp.setSalaire(1500.0);
		Double pourcentage = -0.07;

		// When
		try {
			emp.augmenterSalaire(pourcentage);
			Assertions.fail("Aurait dû planter !");
		} catch (Exception e) {
			// Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le pourcentage ne doit pas être négatif ou null !");
		}

	}



}
