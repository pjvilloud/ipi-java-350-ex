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
	
	/**
	 * Méthode qui teste la méthode embaucheEmploye avec un commercial à plein temps de niveau BTS
	 * @throws EmployeException
	 * @throws EntityExistsException
	 */
	@Test
	public void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException, EntityExistsException {
		
		//Given
		String nom = "LIMAO";
		String prenom = "Twill";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
		Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);
		//Mockito.when(employeRepository.save(employe)).thenReturn(null);
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		//Then
		//Regarde en BDD si l'employé est bien créé (nom, prénom, matricule, salaire,
		//date d'embauche, performance et temps partiel)
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Employe employe = employeCaptor.getValue();
        
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");
        // Salaire = 1521.22 * 1.2 * 1.0
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
		
	}
	
	/**
	 * Méthode qui teste la méthode embaucheEmploye avec un manager à plein temps de niveau BTS
	 * @throws EmployeException
	 * @throws EntityExistsException
	 */
	@Test
	public void testEmbaucheEmployeManagerPleinTempsBTS() throws EmployeException, EntityExistsException {
		
		//Given
		String nom = "LIMAO";
		String prenom = "Twill";
		Poste poste = Poste.MANAGER;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
		Mockito.when(employeRepository.findByMatricule("M00001")).thenReturn(null);
		//Mockito.when(employeRepository.save(employe)).thenReturn(null);
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		//Then
		//Regarde en BDD si l'employé est bien créé (nom, prénom, matricule, salaire,
		//date d'embauche, performance et temps partiel)
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Employe employe = employeCaptor.getValue();
        
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("M00001");
        // Salaire = 1521.22 * 1.2 * 1.0
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
		
	}
	
	/**
	 * Méthode qui teste la méthode embaucheEmploye avec un matricule trop élevé
	 * @throws EmployeException
	 * @throws EntityExistsException
	 */
	@Test
	public void testEmbaucheEmployeLimiteMatricule() throws EmployeException, EntityExistsException {
		
		//Given
		String nom = "LIMAO";
		String prenom = "Twill";
		Poste poste = Poste.TECHNICIEN;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");
		
		// Méthode alternative qui remplace tout le try/catch
		//Assertions.assertThatThrownBy(() -> {employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);}).isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");
		
		//When
		try {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			// IMPORTANT sinon test toujours réussi
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}
		
	}
	
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial (hors cas d'exception)
	 * @param matriculeE1 : le matricule d'un 1er employé (String)
	 * @param performanceE1 : la performance du 1er employé (Integer)
	 * @param matriculeE2 : le matricule d'un 2ème employé (String)
	 * @param performanceE2 : la performance du 2ème employé (Integer)
	 * @param matriculeE3 : le matricule d'un 3ème employé (String)
	 * @param performanceE3 : la performance du 3ème employé (Integer)
	 * @param perfMoyenne : la performance moyenne des 3 employés (Double)
	 * @param caTraite : le chiffre d'affaires traité par le commercial testé (Long)
	 * @param caObjectif : l'objectif de chiffre d'affaires pour le commercial testé (Long)
	 * @param perfCalculee : la nouvelle performance calculée pour le commercial testé (Integer)
	 * @throws EmployeException
	 */
	@ParameterizedTest
	  @CsvSource({
		  "'C55555', 3, 'C88888', 1, 'C33333', 5, 3.0, 50000, 100000, 1",
		  "'C55555', 0, 'C88888', 3, 'C33333', 3, 2.0, 50000, 100000, 1",
		  "'C33333', 2, 'C55555', 4, 'C88888', 3, 3.0, 50000, 100000, 1",
		  "'C55555', 5, 'C88888', 1, 'C33333', 3, 3.0, 90000, 100000, 3",
		  "'C55555', 3, 'C88888', 1, 'C33333', 5, 3.0, 100000, 100000, 3",
		  "'C55555', 5, 'C88888', 7, 'C33333', 9, 7.0, 110000, 100000, 6",
		  "'C55555', 1, 'C88888', 8, 'C33333', 9, 6.0, 130000, 100000, 5",
		  "'C55555', 4, 'C88888', 4, 'C33333', 4, 3.0, 130000, 100000, 9",
		  "'C55555', 0, 'C88888', 0, 'C33333', 0, , 100000, 100000, 1"
	  })
	public void testCalculPerformanceCommercial(String matriculeE1, Integer performanceE1,
			String matriculeE2, Integer performanceE2, String matriculeE3, Integer performanceE3,
			Double perfMoyenne, Long caTraite, Long caObjectif, Integer perfCalculee) throws EmployeException {
		//Given
		Employe e1 = new Employe();
		e1.setMatricule(matriculeE1);
		e1.setPerformance(performanceE1);
		Employe e2 = new Employe();
		e2.setMatricule(matriculeE2);
		e2.setPerformance(performanceE2);
		Employe e3 = new Employe();
		e3.setMatricule(matriculeE3);
		e3.setPerformance(performanceE3);

		Mockito.when(employeRepository.findByMatricule(matriculeE1)).thenReturn(e1);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(perfMoyenne);
		
		//When		
		employeService.calculPerformanceCommercial(matriculeE1, caTraite, caObjectif);

		//Then
		ArgumentCaptor<Employe> commercialCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(1)).save(commercialCaptor.capture());
		e1 = commercialCaptor.getValue();
		Assertions.assertThat(e1.getPerformance()).isEqualTo(perfCalculee);
	}
	
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial avec un CA traité négatif
	 * @throws EmployeException
	 */
	@Test
	public void testCalculPerformanceCommercialExceptionCaTraiteNegatif() throws EmployeException {
		//When
		try {
			employeService.calculPerformanceCommercial("C12345", -5000L, 100000L);
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire traité ne peut être négatif ou null !");
		}

	}
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial avec un CA traité null
	 * @throws EmployeException
	 */
	@Test
	public void testCalculPerformanceCommercialExceptionCaTraiteNull() throws EmployeException {
		//When
		try {
			employeService.calculPerformanceCommercial("C12345", null, 100000L);
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire traité ne peut être négatif ou null !");
		}

	}
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial avec un objectif de CA négatif
	 * @throws EmployeException
	 */
	@Test
	public void testCalculPerformanceCommercialExceptionObjectifNegatif() throws EmployeException {
		//When
		try {
			employeService.calculPerformanceCommercial("C12345", 50000L, -100000L);
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
		}

	}
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial avec un objectif de CA null
	 * @throws EmployeException
	 */
	@Test
	public void testCalculPerformanceCommercialExceptionObjectifNull() throws EmployeException {
		//When
		try {
			employeService.calculPerformanceCommercial("C12345", 50000L, null);
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
		}

	}
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial avec un matricule null
	 * @throws EmployeException
	 */
	@Test
	public void testCalculPerformanceCommercialExceptionMatriculeNull() throws EmployeException {
		//When
		try {
			employeService.calculPerformanceCommercial(null, 50000L, 100000L);
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule ne peut être null et doit commencer par un C !");
		}

	}
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial avec un matricule incorrect
	 * @throws EmployeException
	 */
	@Test
	public void testCalculPerformanceCommercialExceptionMatriculeIncorrect() throws EmployeException {
		//When
		try {
			employeService.calculPerformanceCommercial("T12345", 50000L, 100000L);
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule ne peut être null et doit commencer par un C !");
		}

	}
	
	/**
	 * Méthode qui teste la méthode calculPerformanceCommercial avec un matricule qui n'existe pas en BDD
	 * @throws EmployeException
	 */
	@Test
	public void testCalculPerformanceCommercialExceptionMatriculeNonTrouve() throws EmployeException {
		//Given
		Mockito.when(employeRepository.findByMatricule("C98765")).thenReturn(null);
		//When
		try {
			employeService.calculPerformanceCommercial("C98765", 50000L, 100000L);
			Assertions.fail("Ca aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule C98765 n'existe pas !");
		}

	}

}
