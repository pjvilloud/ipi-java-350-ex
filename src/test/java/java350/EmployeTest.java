package java350;

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
	    public void getNombreAnneeAncienneteNow(){
	        //Given
	        Employe e = new Employe();
	        e.setDateEmbauche(LocalDate.now());

	        //When
	        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

	        //Then
	        org.junit.jupiter.api.Assertions.assertEquals(0, anneeAnciennete.intValue());
	    }

	    @Test
	    public void getNombreAnneeAncienneteNminus2(){
	        //Given
	        Employe e = new Employe();
	        e.setDateEmbauche(LocalDate.now().minusYears(2L));

	        //When
	        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

	        //Then
	        org.junit.jupiter.api.Assertions.assertEquals(2, anneeAnciennete.intValue());
	    }

	    @Test
	    public void getNombreAnneeAncienneteNull(){
	        //Given
	        Employe e = new Employe();
	        e.setDateEmbauche(null);

	        //When
	        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

	        //Then

	        org.junit.jupiter.api.Assertions.assertEquals(0, anneeAnciennete.intValue());
	    }

	    @Test
	    public void getNombreAnneeAncienneteNplus2(){
	        //Given
	        Employe e = new Employe();
	        e.setDateEmbauche(LocalDate.now().plusYears(2L));

	        //When
	        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

	        //Then
	        org.junit.jupiter.api.Assertions.assertEquals(0, anneeAnciennete.intValue());
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
	        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

	        //When
	        Double prime = employe.getPrimeAnnuelle();

	        //Then
	        org.junit.jupiter.api.Assertions.assertEquals(primeAnnuelle, prime);

	    }

	
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
