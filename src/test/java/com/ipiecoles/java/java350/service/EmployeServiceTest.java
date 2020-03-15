package com.ipiecoles.java.java350.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;

@ExtendWith(MockitoExtension.class)

public class EmployeServiceTest {
	
	@InjectMocks
	private EmployeService employeService;
	
	@Mock
	private EmployeRepository employeRepository;
	
	@BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }
	
	@Test
	public void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException {
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		//cas nominal et donc d'exception
		//findLastMatricule => 00345 / null
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
		
		//findByMatricule => null
		Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		//Then
		//BDD si l'employé est bien crée(nom, prénom, matricule, salaire, date d'embauche, performance, temps partiel)
		//Initialisation des capteurs d'argument
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		//Employe employe = employeArgumentCaptor.getValue();
		//Employe employeVerif = new Employe(nom, prenom...)
		//Assertions.assertThat(employe).isEqualTo(nom);
		Assertions.assertThat(employeArgumentCaptor.getValue().getNom()).isEqualTo("Doe");
		Assertions.assertThat(employeArgumentCaptor.getValue().getPrenom()).isEqualTo("John");
		Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("C00346");
		Assertions.assertThat(employeArgumentCaptor.getValue().getDateEmbauche()).isEqualTo(LocalDate.now());
		//1521.22 * 1,2 * 1.0
		Assertions.assertThat(employeArgumentCaptor.getValue().getSalaire()).isEqualTo(1825.46);
		Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employeArgumentCaptor.getValue().getTempsPartiel()).isEqualTo(1.0);
		
		/*Assertions.assertThat(
				employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
						).isEqualTo(
								LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
						Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
						Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
						Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
						//1521.22 * 1,2 * 1.0
						Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);*/
							
	}
	
	@Test
	public void testEmbaucheEmployeCommercialSansMatricule() throws EmployeException {
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		//cas nominal et donc d'exception
		//findLastMatricule => 00345 / null
		Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
		
		//findByMatricule => null
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		//Then
		//BDD si l'employé est bien crée(nom, prénom, matricule, salaire, date d'embauche, performance, temps partiel)
		//Initialisation des capteurs d'argument
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		//Employe employe = employeArgumentCaptor.getValue();
		//Employe employeVerif = new Employe(nom, prenom...)
		//Assertions.assertThat(employe).isEqualTo(nom);
		Assertions.assertThat(employeArgumentCaptor.getValue().getNom()).isEqualTo("Doe");
		Assertions.assertThat(employeArgumentCaptor.getValue().getPrenom()).isEqualTo("John");
		Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("C00001");
		Assertions.assertThat(employeArgumentCaptor.getValue().getDateEmbauche()).isEqualTo(LocalDate.now());
		//1521.22 * 1,2 * 1.0
		Assertions.assertThat(employeArgumentCaptor.getValue().getSalaire()).isEqualTo(1825.46);
		Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employeArgumentCaptor.getValue().getTempsPartiel()).isEqualTo(1.0);
							
	}
	
	public void testEmbaucheEmployeLimiteMatricule() throws EmployeException {
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");
		
		//When/Then AssertJ
        Assertions.assertThatThrownBy(() ->{employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);})
                .isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");
		
		//When
		try {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			Assertions.fail("Aurait dû planter !");
			
		} catch (Exception e) {
			// Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte");
			
		}
	}
	
	@Test
	public void testCalculPerformanceCommercial() throws EmployeException {
		//Given
		Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 3000d, 4, 1.0);
		
		String matricule = "C00001";
		Long caTraite = 30000l;
		Long objectigCa = 40000l;
		
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(employe);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);
		
		//Then
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("C00001");
		Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(1);
							
	}
	
	@Test
	public void testCalculPerformanceCommercialCaTraiteNull() throws EmployeException {
		//Given
		String matricule = "C00001";
		Long caTraite = null;
		Long objectigCa = 40000l;
		
		//When/Then
		Assertions.assertThatThrownBy(() ->{employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);})
        .isInstanceOf(EmployeException.class).hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
							
	}
	
	@Test
	public void testCalculPerformanceCommercialObjectifCaNull() throws EmployeException {
		//Given
		String matricule = "C00001";
		Long caTraite = 30000l;
		Long objectigCa = null;
		
		//When/Then
		Assertions.assertThatThrownBy(() ->{employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);})
        .isInstanceOf(EmployeException.class).hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
							
	}
	
	@Test
	public void testCalculPerformanceCommercialMatriculeErrone() throws EmployeException {
		//Given
		String matricule = "M00001";
		Long caTraite = 30000l;
		Long objectigCa = 40000l;
		
		//When/Then
		Assertions.assertThatThrownBy(() ->{employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);})
        .isInstanceOf(EmployeException.class).hasMessage("Le matricule ne peut être null et doit commencer par un C !");
							
	}
	
	@Test
	public void testCalculPerformanceCommercialMatriculeNull() throws EmployeException {
		//Given
		Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 3000d, 4, 1.0);
		String matricule = "C00001";
		Long caTraite = 30000l;
		Long objectigCa = 40000l;
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
		
		//When/Then
		Assertions.assertThatThrownBy(() ->{employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);})
        .isInstanceOf(EmployeException.class).hasMessage("Le matricule " + matricule + " n'existe pas !");
							
	}
	
	@Test
	public void testCalculPerformanceCommercialMoins20() throws EmployeException {
		//Given
		Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 3000d, 4, 1.0);
		
		String matricule = "C00001";
		Long caTraite = 20000l;
		Long objectigCa = 40000l;
		
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(employe);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);
		
		//Then
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("C00001");
		Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(1);
							
	}
	
	@Test
	public void testCalculPerformanceCommercialMoins5() throws EmployeException {
		//Given
		Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 3000d, 4, 1.0);
		
		String matricule = "C00001";
		Long caTraite = 39000l;
		Long objectigCa = 40000l;
		
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(employe);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);
		
		//Then
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("C00001");
		Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(5);
							
	}
	
	@Test
	public void testCalculPerformanceCommercialPlus5() throws EmployeException {
		//Given
		Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 3000d, 4, 1.0);
		
		String matricule = "C00001";
		Long caTraite = 44000l;
		Long objectigCa = 40000l;
		
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(employe);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);
		
		//Then
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("C00001");
		Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(6);
							
	}
	
	@Test
	public void testCalculPerformanceCommercialPlus20() throws EmployeException {
		//Given
		Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 3000d, 4, 1.0);
		
		String matricule = "C00001";
		Long caTraite = 60000l;
		Long objectigCa = 40000l;
		
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(employe);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectigCa);
		
		//Then
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("C00001");
		Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(9);
							
	}

}