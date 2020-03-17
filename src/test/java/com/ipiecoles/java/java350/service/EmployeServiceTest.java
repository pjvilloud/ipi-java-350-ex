package com.ipiecoles.java.java350.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityExistsException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {
	@InjectMocks
	public EmployeService employeService;
	@Mock
	public EmployeRepository employeRepository;

	@Test
	public void testFindByImmatNotFound() throws EntityExistsException, EmployeException {

		// Given
		String prenom = "Nasim";
		String nom = "Ibrahimi";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;

		// findLastMatrcule => 00345 /null
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");

		// findByMatricule => null
		// l'id après celui qu'on donné dans la prmière parite (le suivant)
		Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);

		// When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

		// Then
		// après l'initialisation de l'employé et sa création on
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
		Employe employe = employeCaptor.getValue();
		Assertions.assertThat(employe.getNom()).isEqualTo(nom);
		Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
		Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");
		Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
		Assertions
				.assertThat(employeCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
				.isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
		Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
		Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		// 15121.22 *1.2*170 le calcule de salaire
		Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);

	}

	@Test
	public void testLimteMatricule() throws EntityExistsException, EmployeException {

		// Given
		String prenom = "Nasim";
		String nom = "Ibrahimi";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;

		// findLastMatrcule => 00345 /null
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

		// When
		try {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			Assertions.fail("Aurait du planter");// if the exception is not execute this message tell as that the test
													// is not executed alors que ca dervait être executé
		} catch (Exception ex) {
			// Then verification du lancement de l'exeption
			Assertions.assertThat(ex).isInstanceOf(EmployeException.class); // verify que la classe d'exption est bien
																			// celle donnée
			Assertions.assertThat(ex.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}

		// la version simplifiée de try catch exception
		Assertions.assertThatThrownBy(() -> {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		}).isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");
	}

	/*********************************************
	 * EXERCICE
	 *********************************************/

	@ParameterizedTest(name = "performance pour le matricuel {0}")
	@CsvSource({ "'C00001',2,85000,100000,1,1", // entre -5 et + 5
			"'C00002',2,50000,100000,2,1", // inférieur entre 20% et 5%
			"'C00003',3,95000,100000,3,3", // entre -5% et +5%
			"'C00004',1,115000,100000,2,2", // supérieur entre 5% et 20%
			"'C00005',1,125000,100000,5,5", // supérieur de plus de 20%
			"'C00006',2,110000,100000,2,4", // supérieure à la moyenne des performances
	})

	public void calculPerformanceCommercialTest(String matricule, Integer performance, Long caTraite, Long objectifCa,
			Double performanceMoyenne, Integer performanceCalcule) throws EmployeException {

		// Given
		Employe employe = new Employe();
		employe.setMatricule(matricule);
		employe.setPerformance(performance);
		Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(performanceMoyenne);

		// When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

		// Then
		ArgumentCaptor<Employe> captor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(1)).save(captor.capture());
		Assertions.assertThat(captor.getValue().getPerformance()).isEqualTo(performanceCalcule);
	}

	/*
	 * Si le catraitement est null, il faut que notre code lance une exception
	 */
	@Test
	public void calculePerformanceCtraitNullTest() {
		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial("C21235", null, 150000L);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
	}

	/*
	 * Si le catraitement est inférieur à zero, il faut que notre code lance une
	 * exception
	 */
	@Test
	public void calculePerformanceCtraitNegatifTest() {
		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial("C21235", -45000L, 150000L);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
	}

	/*
	 * Si l'objectif est null, il faut que notre code lance une exception
	 */
	@Test
	public void calculePerformanceObjectifNullTest() {
		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial("C21235", 130000L, null);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
	}

	/*
	 * Si l'objectif est négatif, il faut que notre code lance une exception
	 */
	@Test
	public void calculePerformanceObjectifNegatifTest() {
		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial("C21235", 130000L, -15000L);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
	}

	/*
	 * Si le matricule null, il faut que notre code lance une exception
	 */
	@Test
	public void testMatriculeNull() {

		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial(null, 130000L, 150000L);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("Le matricule ne peut être null et doit commencer par un C !");
	}

	/*
	 * Si le matricule commence pas par C, il faut que notre code lance une
	 * exception
	 */
	@Test
	public void testMatriculeC() {

		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial("T21235", 130000L, 150000L);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("Le matricule ne peut être null et doit commencer par un C !");
	}

	/*
	 * si la méthode findByMatricule renvoi null, le programe lance une exception
	 */
	@Test
	public void testFindByMaticuleNull() {
		// Given
		String matricule = "C45688";
		Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(null);

		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial(matricule, 130000L, 150000L);
		}).isInstanceOf(EmployeException.class).hasMessage("Le matricule " + matricule + " n'existe pas !");

	}
}
