package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import javax.persistence.EntityExistsException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
	public void testEmbaucheEmployeCommercialPleinTempsBTSMatriculesExistant() throws EntityExistsException, EmployeException {
		
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		//findLastMatricule => 00345 / null
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
		//findByMatricule => null
		Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		//Then
		//verifier dans BDD si l'employe est bien créé (nom, prenom, matricule, salaire, date d'embauche, performance, temps partiel)
		
		//initialisation de capteur d'argument
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Employe emp = employeCaptor.getValue();
        Assertions.assertThat(emp.getNom()).isEqualTo(nom);
        Assertions.assertThat(emp.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(emp.getMatricule()).isEqualTo("C00346");
        //salaire base 1521.22 * coeff etudes 1.2 * temps partiel 1.0
        Assertions.assertThat(emp.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(emp.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(emp.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(emp.getTempsPartiel()).isEqualTo(tempsPartiel);
	}
	
	@Test
	public void testEmbaucheEmployeCommercialTempsPartielBTSMatriculeInitiale() throws EntityExistsException, EmployeException {
		
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.MASTER;
		Double tempsPartiel = null;
		//findLastMatricule => 00345 / null
		Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
		//findByMatricule => null
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		//Then
		//verifier dans BDD si l'employe est bien créé (nom, prenom, matricule, salaire, date d'embauche, performance, temps partiel)
		
		//initialisation de capteur d'argument
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Employe emp = employeCaptor.getValue();
        Assertions.assertThat(emp.getNom()).isEqualTo(nom);
        Assertions.assertThat(emp.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(emp.getMatricule()).isEqualTo("C00001");
        //salaire base 1521.22 * coeff etudes 1.4
        Assertions.assertThat(emp.getSalaire()).isEqualTo(2129.71);
        Assertions.assertThat(emp.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(emp.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(emp.getTempsPartiel()).isEqualTo(tempsPartiel);
	}
	
	
	@Test
	public void testEmbaucheEmployeLimiteMatricule() throws EntityExistsException, EmployeException {
		
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.MASTER;
		Double tempsPartiel = 1.0;
		//findLastMatricule => 99999
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");
		
		
		//When/Then AssertJ
		Assertions.assertThatThrownBy(() -> {employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);})
			.isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");
		
		//When
		try {
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		Assertions.fail("Aurait dû planter !");
		} catch (Exception e) {
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}
		
		
	}



}
