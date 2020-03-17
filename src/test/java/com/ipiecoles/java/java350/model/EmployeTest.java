package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.exception.EmployeException;

public class EmployeTest {

	// 2 ans avant aujourd'hui => 2 années d'ancienneté
	@Test
	public void testAcnienneteDateEmbaucheNmoins2() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(2);
	}

	// Date dans le futur => 0
	@Test
	public void testAcnienneteDateEmbaucheNplus2() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

	// Date aujourd'hui => 0
	@Test
	public void testAcnienneteDateEmbaucheAujourdhui() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

	// Date NUll => 0
	@Test
	public void testAcnienneteDateEmbaucheNull() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

	// test paramétré
	@ParameterizedTest(name = "immat {0} est valide : {4}")
	@CsvSource({ "'M65222',,2,1,1900", "'T52200',1,3,1,1300 ", "'C12335',4,10,1,5300", "'M54871',,5,1,2200 ",
			"'T54112',,2,1,1200 ", "'C78522',3,1,1,3400", "'T12345',1,0,1,1000 ", "'T12345',1,0,0.5,500 ",
			"'M12345',1,0,1,1700 ", ",1,0,1,1000 ", })

	public void testCheckPrimeAnnuelle(String immat, Integer performance, Integer NbAnneeAncienite, Double tempPartiel,
			Double prime) {
		// Given
		Employe employe = new Employe();
		employe.setTempsPartiel(tempPartiel);
		employe.setMatricule(immat);
		employe.setDateEmbauche(LocalDate.now().minusYears(NbAnneeAncienite));
		employe.setPerformance(performance);

		// when
		Double primecalcule = employe.getPrimeAnnuelle();

		// Then
		Assertions.assertThat(primecalcule).isEqualTo(prime);
	}

	/*
	 * les paramètres d'éntres => salaire , pourcentage d'augmentation, salaire;
	 * J'ai considéré que le salaire n'est jamais null ou inférieur à la salaire de
	 * base et que la vérification de ces deux cas s'est faite, au moment de la
	 * création de l'employé.
	 * 
	 * je l'ai autorisé un pourcentage supérrieur à 1.0; sinon je pourrais gérér le
	 * cas avec une exception et déterminé une valeur par défaut si le poucentage
	 * est supérieur à 100%; et mettre une verification dans la méthode
	 * 
	 * comme le type de pourcentage dans la méthode est double, donc il ne recoit
	 * pas une valeur null comme pourcentage. du coup, il lance une execption de
	 * ParamètreException si l'utilisateur ne le respécte pas.
	 */

	@ParameterizedTest(name = "augmentation reussie: salaire initiale : {0} et la salaire actuelle: {2}")
	@CsvSource({ "1800,0.25,2250 ", "2200,0,2200", "2000,1.1,2000", "1700,-0.7,1700 " })

	/**
	 * Ce test vérifie le bon fonctionnement de la méhtode augmenter le salaire
	 * selon le pourcentage donné
	 * 
	 * @param salaire       le salaire essentiel de l'employé
	 * @param pourcentage   le pourcentage d'augmentation
	 * @param salaireFinale le salaire de l'emloyé après l'augmentaion
	 */
	public void testAugmenterSalaire(Double salaire, double pourcentage, Double salaireFinale) throws EmployeException {

		// Given
		Employe employe = new Employe();
		employe.setSalaire(salaire);

		// when
		employe.augmenterSalaire(pourcentage);
		Double salaireAfterAugmentation = employe.getSalaire();

		// Then
		Assertions.assertThat(salaireAfterAugmentation).isEqualTo(salaireFinale);
	}
}
