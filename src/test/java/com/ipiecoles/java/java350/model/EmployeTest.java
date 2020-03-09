package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test; //Junit 5 : org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.model.Employe;

public class EmployeTest {
	
// -----------  Exercice 1 - Tests unitaires classiques  -----------  
	// Employé  2 années d'ancienneté
  @Test 
  public void testAncienneteDateEmbauche(){
      //Given = Initialisation des données d'entrée
	  Employe employe = new Employe();
	  employe.setDateEmbauche(LocalDate.now().minusYears(2));
      
      //When = Exécution de la méthode à tester
	  Integer nbAnnee =  employe.getNombreAnneeAnciennete();

      //Then = Vérifications de ce qu'a fait la méthode (assertj)
	  Assertions.assertThat(nbAnnee).isEqualTo(2);
  	}
  	
  	// Date dans le futur => 0
  @Test 
  public void testAncienneteDateEmbaucheNplus2(){
      //Given
	  Employe employe = new Employe();
	  employe.setDateEmbauche(LocalDate.now().plusYears(2));
      
      //When 
	  Integer nbAnnee =  employe.getNombreAnneeAnciennete();

      //Then
	  Assertions.assertThat(nbAnnee).isEqualTo(0);
  	}
  
//Date aujourd'hui => 0
 @Test 
 public void testAncienneteDateEmbaucheAujourdhui(){
     //Given
	  Employe employe = new Employe();
	  employe.setDateEmbauche(LocalDate.now());
     
     //When 
	  Integer nbAnnee =  employe.getNombreAnneeAnciennete();

     //Then
	  Assertions.assertThat(nbAnnee).isEqualTo(0);
 	}
 
//Date embauche indéfinie => 0
@Test 
public void testAncienneteDateEmbaucheNull(){
    //Given
	  Employe employe = new Employe();
	  employe.setDateEmbauche(null);
    
    //When 
	  Integer nbAnnee =  employe.getNombreAnneeAnciennete();

    //Then
	  Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

// -----------  Exercice 2 - Tests paramétrés -----------  

// test de getPrimeAnnuelle
// matricule, performance, date d'embauche, temps partiel, prime
  
  @ParameterizedTest(name = "test {0} est valide : {1}")
  @CsvSource({
          "1, 'T12345', 0, 1.0, 1000.0",
          "1, 'T12345', 0, 0.5, 500.0",
          "1, 'M12345', 0, 1.0, 1700.0",
          "2, 'T12345', 0, 1.0, 2300.0",

  })
  void testGetPrimeAnnuelle(Integer performance, String matricule, Integer nbAnneeAnciennete, 
		  Double tempsPartiel, Double prime) {
      // Given
	  Employe employe = new Employe();
	  employe.setMatricule(matricule);
	  employe.setPerformance(performance);
	  employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
	  employe.setTempsPartiel(tempsPartiel);
	  
	  
	  // When
	  Double primeCalculee = employe.getPrimeAnnuelle();
	  
	  // Then
	  Assertions.assertThat(primeCalculee).isEqualTo(prime);
	  

  }









}