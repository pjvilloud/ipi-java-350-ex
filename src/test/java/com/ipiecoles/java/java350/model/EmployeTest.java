package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import javax.swing.JSpinner.DateEditor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EmployeTest {
	
	@Test
	public void getNombreAnneeAncienneteNow(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(0);
    }
	
	@Test
    public void getNombreAnneeAncienneteMinus3(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(3));

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(-3);
    }
	
	@Test
    public void getNombreAnneeAnciennetePlus3(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(3);
    }
	
	@Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(0);
    }
	
	
	 @ParameterizedTest
	    @CsvSource({
	    	"1, 'T32165', 0, 1.1, 1000",
	    	"1, 'T32165', 3, 0.6, 2000"
	    })
	 
	 
	public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        
		 //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbYearsAnciennete));
        employe.setTempsPartiel(tempsPartiel);
        
        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeAnnuelle).isEqualTo(prime);

    }
	
}
