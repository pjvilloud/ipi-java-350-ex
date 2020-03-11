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
	  
	 /**
	  * Test de la méthode GetPrimeAnnuelle
	  * @param matricule : le matricule à considérer
	  * @param performance : la performance à considérer
	  * @param anneesAnciennete : le nombre d'années d'ancienneté
	  * @param tempsPartiel : le taux d'activité de l'employé
	  * @param prime : la prime calculée
	  */
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
	  
	 /**
	  * Test de la méthode augmenterSalaire
	  * @param salaire : le salaire initial
	  * @param pourcentage : le pourcentage d'augmentation
	  * @param salaireAugmente : le salaire augmenté
	  */
	  @ParameterizedTest
	  @CsvSource({
	          "2000.0, 0.1, 2200.0",
	          "0.0, 0.5, 0.0",
	          "1000.0, -0.1, 1000.0",
	          ", 0.5,"
	  })
	  public void testAugmenterSalaire(Double salaire, Double pourcentage, Double salaireAugmente) {
		  //Given
		  Employe e = new Employe();
		  e.setSalaire(salaire);
		  //When
		  e.augmenterSalaire(pourcentage);
		  //Then
		  Assertions.assertThat(e.getSalaire()).isEqualTo(salaireAugmente);
	  }
	  

	 /**
	  * Test de la méthode getNbRtt
	  * @param date : la date à considérer
	  * @param tempsPartiel : le taux d'activité
	  * @param nbRtt : le nombre de RTT calculé
	  */
	  @ParameterizedTest
	  @CsvSource({
		  // Année 2029, commence un lundi,
		  // 365 jours - 218 jours ouvrés - 104 jours de weekend
		  // - 9 jours fériés en semaine - 25 jours de congé = 9 jours de RTT"
		  "'2029-01-01', 1.0, 9",
		  // 2024, lundi, 366 - 218 - 104 - 10 - 25 = 9
		  "'2024-01-01', 1.0, 9",
          // 2019, mardi, 365 - 218 - 104 - 10 - 25 = 8
          "'2019-01-01', 1.0, 8",
          //2036, mardi, 366 - 218 - 104 - 10 - 25 = 9
          "'2036-01-01', 1.0, 9",
          //2025, mercredi, 365 - 218 - 104 - 10 - 25 = 8
          "'2025-01-01', 1.0, 8",
          //2020, mercredi, 366 - 218 - 104 - 9 - 25 = 10
          "'2020-01-01', 1.0, 10",
          //2026, jeudi, 365 - 218 - 104 - 9 - 25 = 9
          "'2026-01-01', 1.0, 9",
          //2032, jeudi, 366 - 218 - 104 - 7 - 25 = 12
          "'2032-01-01', 1.0, 12", 
          //2021, vendredi, 365 - 218 - 104 - 7 - 25 = 11
          //impossible de tester une année bissextile commençant par un vendredi
          //sans obtenir une NullPointerException => Années testées : 2016 et 2044
          //Années trop anciennes ou trop éloignées => erreur dans la méthode Entreprise.joursFeries()
          "'2021-01-01', 1.0, 11",
          //2022, samedi, 365 - 218 - 105 - 7 - 25 = 10
          "'2022-01-01', 1.0, 10",
          //2028, samedi, 366 - 218 - 106 - 9 - 25 = 8
          "'2028-01-01', 1.0, 8",
          //2034, dimanche, 365 - 218 - 105 - 9 - 25 = 8
          "'2034-01-01', 1.0, 8",
          //2040, dimanche, 366 - 218 - 105 - 5 - 25 = 13
          "'2040-01-01', 1.0, 13",
          //Tests de temps partiel
          "'2028-01-01', 0.5, 4",
          "'2034-01-01', 0.8, 7",
          "'2040-01-01', 0.5, 7"
	  })
	  public void testGetNbRtt(LocalDate date, Double tempsPartiel, Integer nbRtt) {
		  
		  //Given
		  Employe e = new Employe();
		  e.setTempsPartiel(tempsPartiel);
		  //When
		  Integer nbRttCalcule = e.getNbRtt(date);
		  //Then
		  Assertions.assertThat(nbRttCalcule).isEqualTo(nbRtt);
	  }

}
