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
	 * Tests paramétrés
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
	
	/*
	 * Tests sur méthode augmenterSalaire
	 */
	
	
	@Test
    public void testAugmentationNulle() {
        // Given
        Employe employe = new Employe();
        employe.setSalaire(3000.0);

        // When
        employe.augmenterSalaire(0);
        Double salaireAug = employe.getSalaire();

        // Then
        Assertions.assertThat(salaireAug).isEqualTo(3000.0);
    }
	
	@Test
    public void testAugmentationNegative() {
        // Given
        Employe employe = new Employe();
        employe.setSalaire(3000.0);

        // When
        employe.augmenterSalaire(-0.5);
        Double salaireAug = employe.getSalaire();

        // Then
        Assertions.assertThat(salaireAug).isEqualTo(3000.0);
    }
	
	 @Test
	    public void testAugmentationPositive() {
	        // Given
	        Employe employe = new Employe();
	        employe.setSalaire(3000.0);

	        // When
	        employe.augmenterSalaire(0.1);
	        Double salaireAug = employe.getSalaire();

	        // Then
	        Assertions.assertThat(salaireAug).isEqualTo(3300.0);

	    }
	 
	 /*
	 @ParameterizedTest
	    @CsvSource({
	            "3000 ,-0.5, 3000",
	            "3000, 0, 3000",
	            "3000 , 0.1, 3300",
	    })
	    public void testAugmenterSalaire(
	            Double salaire,
	            double pourcentage,
	            Double salaireAug
	    ) {
	        // given
	        Employe emp = new Employe();
	        emp.setSalaire(salaire);

	        // when
	        emp.augmenterSalaire(pourcentage);
	        Double newSalaire = emp.getSalaire();
	        // then
	        Assertions.assertThat(newSalaire).isEqualTo(salaireAug);
	    }
	*/
	 
}
