package com.ipiecoles.java.java350.model;

import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe(
        		"Doe", 
        		"John", 
        		matricule, 
        		LocalDate.now().minusYears(nbYearsAnciennete), 
        		Entreprise.SALAIRE_BASE, performance, 
        		tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);

    }
  
    @Test
	public void testAugmenterSalaire0() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100d);
		employe.augmenterSalaire(0);

		// When
		Double nouveauSalaire = employe.getSalaire();

		// Then
		Assertions.assertEquals(nouveauSalaire, (Double) 100.0);
	}
    
	@Test
	public void testAugmenterSalaire50() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100.0);
		
		// When
		employe.augmenterSalaire(50);
		
		// Then
		Assertions.assertEquals(employe.getSalaire(), (Double) 150.0);
	}
	
	@Test
	public void testAugmenterSalaire200() {
		// Given
		Employe employe = new Employe();
		employe.setSalaire(100d);

		// When
		employe.augmenterSalaire(200);
		Double nouveauSalaire = employe.getSalaire();

		// Then
		Assertions.assertEquals(nouveauSalaire, (Double) 300.0);
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
		Assertions.assertEquals(nouveauSalaire, (Double) 50.0);
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
		Assertions.assertEquals(exception, (Double) 100.0);
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
		Assertions.assertEquals(nbAnnee, (Integer) 0);
	}

	@Test
	public void testGetNombreAnneeAncienneteNull() {
		// Given
		Employe e = new Employe();
		e.setDateEmbauche(null);

		// When
		Double nbAnnee = (double)e.getNombreAnneeAnciennete();

		// Then
		Assertions.assertEquals(nbAnnee, (Double) 0.0);		
	}

	@Test
	public void testGetNombreAnneeAncienneteNmoins2() {
		// Given
		Employe e = new Employe();
		e.setDateEmbauche(LocalDate.now().minusYears(2));

		// When
		Integer nbAnnee = e.getNombreAnneeAnciennete();

		// Then
		Assertions.assertEquals(nbAnnee, (Integer) 2);
	}

	@Test
	public void testGetNombreAnneeAncienneteNplus2() {
		// Given
		Employe e = new Employe();
		e.setDateEmbauche(LocalDate.now().plusYears(2));

		// When
		Integer nbAnnee = e.getNombreAnneeAnciennete();

		// Then
		Assertions.assertEquals(nbAnnee, (Integer) 0);
	}

	@ParameterizedTest(name = "Année {0} : {1} jours de RTT avec temps de travail : {2}")
	@CsvSource({
		"2019, 9, 1.0",
		"2022, 11, 1.0",
        "2040, 10, 1.0",
        "2028, 8, 1.0",
	})
    public void testGetNbRttParametres(int date, int nbRttAttendus, Double tempsPartiel) {
        // Given
        Employe employe = new Employe();
        employe.setTempsPartiel(tempsPartiel);
        LocalDate annee = LocalDate.of(date, 1, 1);
        
        // When
        int nbJoursRtt = employe.getNbRtt(annee);
        
        // Then
        Assertions.assertEquals(nbRttAttendus, nbJoursRtt);
    }
}