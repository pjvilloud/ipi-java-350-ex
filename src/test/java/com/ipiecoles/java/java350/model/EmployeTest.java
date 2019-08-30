package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {

	@Test
	public void testAugmenterSalaire0() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100d);
		employe.augmenterSalaire(0);

		// When
		Double nouveauSalaire = employe.getSalaire();

		// Then
		Assertions.assertThat(nouveauSalaire).isEqualTo(100);
	}

	@Test
	public void testAugmenterSalaire50() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100d);
		employe.augmenterSalaire(50);

		// When
		Double nouveauSalaire = employe.getSalaire();

		// Then
		Assertions.assertThat(nouveauSalaire).isEqualTo(150);
	}

	@Test
	public void testAugmenterSalaire200() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100d);
		employe.augmenterSalaire(200);

		// When
		Double nouveauSalaire = employe.getSalaire();

		// Then
		Assertions.assertThat(nouveauSalaire).isEqualTo(300);
	}

	@Test
	public void testAugmenterSalaireNegatif50() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100d);
		employe.augmenterSalaire(-50);

		// When
		Double nouveauSalaire = employe.getSalaire();

		// Then
		Assertions.assertThat(nouveauSalaire).isEqualTo(50);
	}

	@Test
	public void testAugmenterSalaireNegatif101() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100d);
		IllegalArgumentException exception = null;

		// When
		try {
			employe.augmenterSalaire(-101);
		} catch (IllegalArgumentException e) {
			exception = e;
		}

		// Then
		Assertions.assertThat(exception).isNotNull();
	}

	@Test
	public void testGetNombreAnneeAncienneteNow() {
		// Given
		Employe employe = new Employe();
		LocalDate dateEmbauche = LocalDate.now();
		employe.setDateEmbauche(dateEmbauche);

		// When
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

	@Test
	public void testGetNombreAnneeAncienneteNull() {
		// Given
		Employe e = new Employe();
		e.setDateEmbauche(null);

		// When
		Integer nbAnnee = e.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

	@Test
	public void testGetNombreAnneeAncienneteNmoins2() {
		// Given
		Employe e = new Employe();
		e.setDateEmbauche(LocalDate.now().minusYears(2));

		// When
		Integer nbAnnee = e.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(2);
	}

	@Test
	public void testGetNombreAnneeAncienneteNplus2() {
		// Given
		Employe e = new Employe();
		e.setDateEmbauche(LocalDate.now().plusYears(2));

		// When
		Integer nbAnnee = e.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

}
