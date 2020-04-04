package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

	@InjectMocks
	private EmployeService employeService;
	@Mock
	private EmployeRepository employeRepository;
	
	@Autowired
	private EmployeRepository employeRepositorybdd;

	@Test
	public void embaucheEmployeTest() throws EmployeException {
		// Given
		String nom = "Dao";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00356");
		Mockito.when(employeRepository.findByMatricule("C00357")).thenReturn(null);

		// When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

		// Then
		Employe employe = new Employe(nom, prenom, "C00357", LocalDate.now(), 1825.46, Entreprise.PERFORMANCE_BASE,
				1.0);
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
		Assertions.assertThat(employeCaptor.getValue()).isEqualTo(employe);

	}

	@Test
	public void embaucheEmployeTestNull() throws EmployeException {
		// Given
		String nom = "Dao";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

		// When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

		// Then
		Employe employe = new Employe(nom, prenom, "C00001", LocalDate.now(), 1825.46, Entreprise.PERFORMANCE_BASE,
				1.0);
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
		Assertions.assertThat(employeCaptor.getValue()).isEqualTo(employe);

	}

	@Test
	public void embaucheEmployeTestLimite() throws EmployeException {
		// Given
		String nom = "Dao";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

		// when/Then assertJ
		Assertions.assertThatThrownBy(() -> {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		}).isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");
		// When
		try {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			Assertions.fail(" Aurait du planter");
		} catch (Exception e) {
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}

		// Then
	}

	@ParameterizedTest
	@CsvSource({ "'M11111',10000,10000", ",10000,10000" })
	public void calculPerformanceCommercialex1(String matricule, Long caTraite, Long objectifCa) {
		// given
		Employe employe = new Employe();
		employe.setMatricule(matricule);

		// when,Then
		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("Le matricule ne peut être null et doit commencer par un C !");

	}

	@ParameterizedTest
	@CsvSource({ "'C11111',10000,-10000", "C11111,10000," })
	public void calculPerformanceCommercialex2(String matricule, Long caTraite, Long objectifCa) {
		// given
		Employe employe = new Employe();
		employe.setMatricule(matricule);

		// when,Then
		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");

	}

	@ParameterizedTest
	@CsvSource({ "'C11111',-10000,10000", "C11111,,10000" })
	public void calculPerformanceCommercialex3(String matricule, Long caTraite, Long objectifCa) {
		// given
		Employe employe = new Employe();
		employe.setMatricule(matricule);

		// when,Then
		Assertions.assertThatThrownBy(() -> {
			employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		}).isInstanceOf(EmployeException.class)
				.hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");

	}

	@ParameterizedTest
	@CsvSource({ "'C11111',8500,10000,2,1", "'C11111',8500,10000,10,1", "'C11111',10000,10000,2,1",
			"'C11111',10000,10000,10,1", "'C11111',11200,10000,2,2", "'C11111',11200,10000,10,2",
			"'C11111',13000,10000,2,6", "'C11111',13000,10000,10,5" })
	public void calculPerformanceCommercialGenerale(String matricule, Long caTraite, Long objectifCa,
			Double performanceMoyen, Integer performanceResultat) throws EmployeException {
		// given
		Employe employe = new Employe();
		employe.setMatricule(matricule);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(performanceMoyen);
		Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);

		// when
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

		// Then
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
		Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(performanceResultat);

	}

	@ParameterizedTest
	@CsvSource({ "'C11111',8500,10000,1", "'C11112',10000,10000,1", "'C11113',11200,10000,2",
			"'C11114',13000,10000,6" })
	public void calculPerformanceCommercialBddNominale(String matricule, Long caTraite, Long objectifCa,
			Integer performanceResultat) throws EmployeException {
		// given
		Employe employe = new Employe();
		employe.setMatricule(matricule);

		// when
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

		// Then
		Assertions.assertThat(employeRepositorybdd.findByMatricule(matricule).getPerformance())
				.isEqualTo(performanceResultat);

	}

}
