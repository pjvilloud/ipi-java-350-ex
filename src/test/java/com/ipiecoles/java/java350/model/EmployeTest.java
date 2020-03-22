package com.ipiecoles.java.java350.model;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;  //jUnit 5

import org.apache.commons.lang.ObjectUtils.Null;
import org.assertj.core.api.Assertions; //AsserJ
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EmployeTest {

	//Test unitaire qui test un seul cas
	@Test //Junit 5 import : avec jupiter soit Junit 5 : org.junit.jupiter.api.Test
	public void testAncienneteDateEmbaucheNmoins2() {
		
		//Employé dateEmbauche avec date 2ans avant aujourd'hui => 
		//2 années d'ancienneté
		
			//Given = Initialisation des données d'entrée
			Employe employe = new Employe();
			employe.setDateEmbauche(LocalDate.now().minusYears(2));
			
			//When = Exécution de la méthode à tester
			Integer nbAnnees = employe.getNombreAnneeAnciennete();
			
			//Then = Vérifications de ce qu'a fait la méthode
			Assertions.assertThat(nbAnnees).isEqualTo(2);
			//AserJ pour faire des assertions bcp plus lisible que Junit
	}



	@Test
	public void testNbConges() {

		//Given = Initialisation des données d'entrée
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));

		//When = Exécution de la méthode à tester
		Integer nbConges = employe.getNbConges();

		//Then = Vérifications de ce qu'a fait la méthode
		Assertions.assertThat(nbConges).isEqualTo(27);
		//AserJ pour faire des assertions bcp plus lisible que Junit
	}




	
	@Test
	public void testAncienneteDateEmbaucheNplus2() {
		
		//Employé dateEmbauche avec date 2ans après aujourd'hui => 
		//0 années d'ancienneté
		
			//Given = Initialisation des données d'entrée
			Employe employe = new Employe();
			employe.setDateEmbauche(LocalDate.now().plusYears(2));
			
			//When = Exécution de la méthode à tester
			Integer nbAnnees = employe.getNombreAnneeAnciennete();
			
			//Then = Vérifications de ce qu'a fait la méthode
			Assertions.assertThat(nbAnnees).isEqualTo(0);
			//AserJ pour faire des assertions bcp plus lisible que Junit
	}


    @Test
    public void testgetNbrRtt() {

        //Employé dateEmbauche avec date 2ans après aujourd'hui =>
        //0 années d'ancienneté

        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        //When = Exécution de la méthode à tester
        Integer nbRtt = employe.getNbRtt();
        Integer nbRttActuel = employe.getNbRtt(LocalDate.now());


        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbRtt).isEqualTo(nbRttActuel);
        //AserJ pour faire des assertions bcp plus lisible que Junit
    }
	
	//Date aujourd'hui => 0
	@Test
	public void testAncienneteDateEmbaucheAujourdhui() {
		
		//Employé dateEmbauche avec date d'aujourd'hui => 
		//0 années d'ancienneté
		
			//Given = Initialisation des données d'entrée
			Employe employe = new Employe();
			employe.setDateEmbauche(LocalDate.now());
			
			//When = Exécution de la méthode à tester
			Integer nbAnnees = employe.getNombreAnneeAnciennete();
			
			//Then = Vérifications de ce qu'a fait la méthode
			Assertions.assertThat(nbAnnees).isEqualTo(0);
			//AserJ pour faire des assertions bcp plus lisible que Junit
	}
	
	//Date d'embauche indéfinie => 0 (on considère que c 0 dans ce cas)
	@Test
	public void testAncienneteSansDateEmbauche() {
		
		//Employé sans dateEmbauche
		
			//Given = Initialisation des données d'entrée
			Employe employe = new Employe();
			employe.setDateEmbauche(null);
			
			//When = Exécution de la méthode à tester
			Integer nbAnnees = employe.getNombreAnneeAnciennete();
			
			//Then = Vérifications de ce qu'a fait la méthode
			Assertions.assertThat(nbAnnees).isEqualTo(0);
			//AserJ pour faire des assertions bcp plus lisible que Junit
			
	}
	
	
	//Date dans le futur
	//on choisit de renvoyer undefined, null, ou ...
	//
	
	/* Dans la classe : employe.java
	public Integer getNombreAnneeAnciennete() {
        return LocalDate.now().getYear() - dateEmbauche.getYear();
    }
    private LocalDate dateEmbauche;
    */
	
	
	//5 paramètres qui varient
	//Matricule, performance, dateEmbauche, tempsPartiel, prime
		
		@ParameterizedTest
		@CsvSource({
			// Au moins 9 cas, scénarios à gérer, à tester
			
			//Performance, Matricule, dateEmbauche, tempPartiel, prime
			"1, 'T12345', 0, 1.0, 1000.0",
			"1, 'T12345', 0, 0.5, 500.0",
		    "1, 'M12345', 0, 1.0, 1700.0",
		    "2, 'T12345', 0, 1.0, 2300.0",
		    
		    //Matricule, performance, dateEmbauche, tempsPartiel, prime
		    //"'M12345', 2, 0, 0.5, 0",
		    //"'M12345', 2, 2, 1.0, ",
		    //"'M12345', 2, 2, 0.5, ",
		    
		   //Run avec coverage

		        
		})
		public void testPrimeAnnuelle(Integer performance, String Matricule, Integer NombreAnneeAnciennete, Double tempsPartiel, Double prime) {
		    //3 parties pour faire les Tests : Given, When, Then
			
			//Given, 
			Employe employe = new Employe();    //Copie de la classe vide : objet vide
			
			//Propriété de l'objet Employé
			//setter pour mettre à jour les != propriétés de l'objet
			employe.setMatricule(Matricule);
			employe.setDateEmbauche(LocalDate.now().minusYears(NombreAnneeAnciennete));
			employe.setPerformance(performance);
			employe.setTempsPartiel(tempsPartiel);
			
			//When, Ce que je compare dans le test
			Double primeCalculee = employe.getPrimeAnnuelle();
			
			//Then, Faire la comparaison 
			Assertions.assertThat(primeCalculee).isEqualTo(prime);
			
			
			// NombreAnneeAnciennete avec :
			// Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * this.getNombreAnneeAnciennete();
			// 2 cas possibles au moins NombreAnneeAnciennete >1 alors on a une primeAnciennete !=0
			//ou NombreAnneeAnciennete = 0 alors primeAnciennete = 0
			
		
		}
		
		
		
        //Calcule de la prime d'ancienneté
        //Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * this.getNombreAnneeAnciennete();
        //Double prime;
        //Prime du manager (matricule commençant par M) : Prime annuelle de base multipliée par l'indice prime manager
        //plus la prime d'anciennté.
        //if(matricule != null && matricule.startsWith("M")) {
           // prime = Entreprise.primeAnnuelleBase() * Entreprise.INDICE_PRIME_MANAGER + primeAnciennete;
        //}
        //Pour les autres employés en performance de base, uniquement la prime annuelle plus la prime d'ancienneté.
        //else if (this.performance == null || Entreprise.PERFORMANCE_BASE.equals(this.performance)){
            //prime = Entreprise.primeAnnuelleBase() + primeAnciennete;
        //}
        //Pour les employés plus performance, on bonnifie la prime de base en multipliant par la performance de l'employé
        // et l'indice de prime de base.
        //else {
          //  prime = Entreprise.primeAnnuelleBase() * (this.performance + Entreprise.INDICE_PRIME_BASE) + primeAnciennete;
        //}
        //Au pro rata du temps partiel.
        //return prime * this.tempsPartiel;
		
		
		
		
		
		

		// le pour ne doit pas etre null
		// por doit etre +
		// tout est ok
		
		
		@Test
		public void le_pourcentage_ne_doit_pas_etre_nul() {

			Employe employe = new Employe();
			employe.setSalaire(2000.0);
			
			assertThrows(NullPointerException.class, () -> employe.augmenteSalaire(null));

		}
		
		
		
		@Test
		public void le_pourcentage_ne_doit_pas_etre_negative() {

			Employe employe = new Employe();
			employe.setSalaire(2000.0);
			
			assertThrows(IllegalArgumentException.class, () -> employe.augmenteSalaire(-5.0));

		}
		
		
		@Test
		public void le_pourcentage_positif_et_non_null() {

			Employe employe = new Employe();
			employe.setSalaire(2000.0);
			
			employe.augmenteSalaire(10.0);
			
			Assertions.assertThat(employe.getSalaire()).isEqualTo(2200);

		}
		
		/*La f° augmenteSalaire est mieux délimitée avec les tests TDD car on évite ainsi d'oublier des cas extrêmes possibles 
		en écrivant ces tests et en adaptant la fonction augmenteSalaire à chacun des tests. */
		
		
		
		//2021 : l'année est non bissextile, a débuté un vendredi et il y a 7 jours fériés ne tombant pas le week-end.
		//2022 : l'année est non bissextile, a débuté un samedi et il y a 7 jours fériés ne tombant pas le week-end.
		//2032 : l'année est bissextile, a débuté un jeudi et il y a 7 jours fériés ne tombant pas le week-end.
		
		
		//2019 : l'année est non bissextile, a débuté un mardi et il y a 10 jours fériés ne tombant pas le week-end.
		@Test
		public void nombre_jour_ferries_sans_week_end_est_non_bissextile_débutant_un_mardi() {

			Employe employe = new Employe();
			
			
			int nombreJourFeriesSansWeekEnd =  employe.getNombreJourFerierSansWeekend(LocalDate.of(2019, 1, 1));
			
			Assertions.assertThat(nombreJourFeriesSansWeekEnd).isEqualTo(10);

		}



	//1980 : l'année est bissextile, a débuté un mardi et il y a 10 jours fériés ne tombant pas le week-end.
	@Test
	public void nombre_jour_ferries_sans_week_end_est_bissextile_débutant_un_mardi() {

		Employe employe = new Employe();


		int nombreJourFeriesSansWeekEnd =  employe.getNombreJourFerierSansWeekend(LocalDate.of(2036, 1, 1));

		Assertions.assertThat(nombreJourFeriesSansWeekEnd).isEqualTo(10);

	}
		
		//2021 : l'année est non bissextile, a débuté un vendredi et il y a 7 jours fériés ne tombant pas le week-end.
		@Test
		public void nombre_jour_ferries_sans_week_end_est_non_bissextile_débutant_un_vendredi() {

			Employe employe = new Employe();
			
			int nombreJourFeriesSansWeekEnd =  employe.getNombreJourFerierSansWeekend(LocalDate.of(2021, 1, 1));
			
			Assertions.assertThat(nombreJourFeriesSansWeekEnd).isEqualTo(7);

		}
		
		
		//2022 : l'année est non bissextile, a débuté un samedi et il y a 7 jours fériés ne tombant pas le week-end.
		@Test
		public void nombre_jour_ferries_sans_week_end_est_non_bissextile_débutant_un_samedi() {

			Employe employe = new Employe();
			
			int nombreJourFeriesSansWeekEnd =  employe.getNombreJourFerierSansWeekend(LocalDate.of(2022, 1, 1));
			
			Assertions.assertThat(nombreJourFeriesSansWeekEnd).isEqualTo(7);

		}
		
		
		//2032 : l'année est bissextile, a débuté un jeudi et il y a 7 jours fériés ne tombant pas le week-end.
		@Test
		public void nombre_jour_ferries_sans_week_end_est_bissextile_débutant_un_jeudi() {

			Employe employe = new Employe();
			
			int nombreJourFeriesSansWeekEnd =  employe.getNombreJourFerierSansWeekend(LocalDate.of(2032, 1, 1));
			
			Assertions.assertThat(nombreJourFeriesSansWeekEnd).isEqualTo(7);

		}
		
		//2020 : tester le nombre samedi dimanche
		
		@Test
		public void le_nombre_samedi_dimanche_2020() {

			Employe employe = new Employe();
			
			int nombreJourSamediDimanche =  employe.getNombreSamediDimanche(LocalDate.of(2020, 1, 1));
			
			Assertions.assertThat(nombreJourSamediDimanche).isEqualTo(105);

		}

	//2036 : tester le nombre samedi dimanche

	@Test
	public void le_nombre_samedi_dimanche_2036() {

		Employe employe = new Employe();

		int nombreJourSamediDimanche =  employe.getNombreSamediDimanche(LocalDate.of(2036, 1, 1));

		Assertions.assertThat(nombreJourSamediDimanche).isEqualTo(104);

	}

	@Test
	public void le_nombre_samedi_dimanche_2030() {

		Employe employe = new Employe();

		int nombreJourSamediDimanche =  employe.getNombreSamediDimanche(LocalDate.of(2030, 1, 1));

		Assertions.assertThat(nombreJourSamediDimanche).isEqualTo(105);

	}
		
		
	//2021 : tester le nombre samedi dimanche
		
		@Test
		public void le_nombre_samedi_dimanche_2021() {

			Employe employe = new Employe();
			
			int nombreJourSamediDimanche =  employe.getNombreSamediDimanche(LocalDate.of(2021, 1, 1));
			
			Assertions.assertThat(nombreJourSamediDimanche).isEqualTo(105);

		}
	
		
	//2019 : tester le nombre samedi dimanche
		
		@Test
		public void le_nombre_samedi_dimanche_2019() {

			Employe employe = new Employe();
			
			int nombreJourSamediDimanche =  employe.getNombreSamediDimanche(LocalDate.of(2019, 1, 1));
			
			Assertions.assertThat(nombreJourSamediDimanche).isEqualTo(105);

		}
		
		
		//2018 : tester le nombre samedi dimanche
		@Test
		public void le_nombre_samedi_dimanche_2018() {

			Employe employe = new Employe();
			
			int nombreJourSamediDimanche =  employe.getNombreSamediDimanche(LocalDate.of(2018, 1, 1));
			
			Assertions.assertThat(nombreJourSamediDimanche).isEqualTo(104);

		}


}




