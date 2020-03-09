package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EmployeTest {
	
	// dateEmbauche avec date 2 ans avant aujourd'hui => 2 années d'ancienneté
	
	  @Test //Junit 4 : org.junit.Test, Junit 5 : org.junit.jupiter.api.Test
	  public void testAncienneteDateEmbaucheNmoins2() {

		  //Given = Initialisation des données d'entrée
		  Employe e = new Employe();
		  e.setDateEmbauche(LocalDate.now().minusYears(2));

		  //When = Exécution de la méthode à tester
		  Integer nbAnnees = e.getNombreAnneeAnciennete();

		  //Then = Vérifications de ce qu'a fait la méthode
		  // ATTENTION import de assertj
		  Assertions.assertThat(nbAnnees).isEqualTo(2);
		  
	  }
	  
	  // Date dans le futur => 0
	  @Test
	  public void testAncienneteDateEmbaucheNplus2() {

		  //Given = Initialisation des données d'entrée
		  Employe e = new Employe();
		  e.setDateEmbauche(LocalDate.now().plusYears(2));

		  //When = Exécution de la méthode à tester
		  Integer nbAnnees = e.getNombreAnneeAnciennete();

		  //Then = Vérifications de ce qu'a fait la méthode
		  // ATTENTION import de assertj
		  Assertions.assertThat(nbAnnees).isEqualTo(0);
		  
	  }
	  
	  // Date d'aujourd'hui => 0
	  @Test
	  public void testAncienneteDateEmbaucheDateDuJour() {

		  //Given = Initialisation des données d'entrée
		  Employe e = new Employe();
		  e.setDateEmbauche(LocalDate.now());

		  //When = Exécution de la méthode à tester
		  Integer nbAnnees = e.getNombreAnneeAnciennete();

		  //Then = Vérifications de ce qu'a fait la méthode
		  // ATTENTION import de assertj
		  Assertions.assertThat(nbAnnees).isEqualTo(0);
		  
	  }
	  
	  // Date embauche indéfinie => 0
	  @Test
	  public void testAncienneteDateEmbaucheIndefinie() {

		  //Given = Initialisation des données d'entrée
		  Employe e = new Employe();
		  e.setDateEmbauche(null);

		  //When = Exécution de la méthode à tester
		  Integer nbAnnees = e.getNombreAnneeAnciennete();

		  //Then = Vérifications de ce qu'a fait la méthode
		  // ATTENTION import de assertj
		  Assertions.assertThat(nbAnnees).isEqualTo(0);
		  
	  }
	  
	  
	  //@ParameterizedTest(name = "immat {0} est valide : {1}")
	  @ParameterizedTest
	  @CsvSource({
	          "'M00000', 0, 2, 1.0, 1900d",
	          "'T00000', 1, 2, 1.0, 1200d",
	          "'T00000', 5, 2, 1.0, 5500d",
	          "'C00000', 3, 5, 0.5, 1900d",
	          "'C00000', , 5, 1, 1500d",
	          ", , 5, 1, 1500d"
	  })
	  public void testGetPrimeAnnuelle(String matricule, Integer performance,
			  Integer anneesAnciennete, Double tempsPartiel, Double prime) {
		  
	      // Given
		  Employe e = new Employe();
		  e.setDateEmbauche(LocalDate.now().minusYears(anneesAnciennete));
		  e.setMatricule(matricule);
		  e.setTempsPartiel(tempsPartiel);
		  e.setPerformance(performance);
		  
		  // When
		  Double primeCalculee = e.getPrimeAnnuelle();
		  
		  // Then
	      Assertions.assertThat(primeCalculee).isEqualTo(prime);
	  }

}
