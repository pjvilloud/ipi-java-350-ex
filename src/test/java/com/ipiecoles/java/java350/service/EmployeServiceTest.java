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

}
