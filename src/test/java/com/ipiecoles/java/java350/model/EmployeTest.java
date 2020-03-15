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
	 * Tests sur la méthode augmenterSalaire
	 */
	
	// Test pour une augmentation de salaire nulle 
	
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
	
	// Test pour une diminution de salaire de 50%  
	
	@Test
    public void testAugmentationNegative() {
        // Given
        Employe employe = new Employe();
        employe.setSalaire(3000.0);

        // When
        employe.augmenterSalaire(-0.5);
        Double salaireAug = employe.getSalaire();

        // Then
        Assertions.assertThat(salaireAug).isEqualTo(1500.0);
    }
	
	// Test pour une augmentation de salaire de 20% 
	
	@Test
    public void testAugmentationPositive() {
        // Given
        Employe employe = new Employe();
        employe.setSalaire(3000.0);

        // When
        employe.augmenterSalaire(0.2);
        Double salaireAug = employe.getSalaire();

        // Then
        Assertions.assertThat(salaireAug).isEqualTo(3600.0);

    }
	
	// Test pour une diminution de salaire de 100% 
	 
    @Test
    public void testAugmentationMoinsCentPourcents(){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(3000.0);

        //When
        employe.augmenterSalaire(-1.0);
        Double salaireAug = employe.getSalaire();

        //Then
        Assertions.assertThat(salaireAug).isEqualTo(0.0);
    }
    
    // Test pour une augmentation sur un salaire nul 
	 
    @Test
    public void testAugmentationSalaireNul(){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(0.0);

        //When
        employe.augmenterSalaire(1.0);
        Double salaireAug = employe.getSalaire();

        //Then
        Assertions.assertThat(salaireAug).isEqualTo(0.0);
    }
	
	 
	/*
	 *  Test paramétré de la fonction getNbRtt, en faisant varier le taux d'activité et l'année considérée (bissextile ou non)
	 */
	 
	    @ParameterizedTest
	    @CsvSource({
	        "2019, 1.0, 8",
	        "2019, 0.5, 4",
	        "2021, 1.0, 11",
	        "2021, 0.5, 5",
	        "2022, 1.0, 10",
	        "2022, 0.5, 5",
	        "2032, 1.0, 12",	
	        "2032, 0.5, 6"	        
	    })
	    public void getNbRtt(Integer annee, Double tempsPartiel, Integer nbRttAttendu){
	        //Given
	        Employe employe = new Employe();
	        employe.setTempsPartiel(tempsPartiel);
	        LocalDate date = LocalDate.of(annee, 1, 1);

	        //When
	        Integer nbRttCalcule = employe.getNbRtt(date);

	        //Then
	        Assertions.assertThat(nbRttCalcule).isEqualTo(nbRttAttendu);

	    }
	 
}
