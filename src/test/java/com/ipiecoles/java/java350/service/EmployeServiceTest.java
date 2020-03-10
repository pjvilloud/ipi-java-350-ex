package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest 
{
	@InjectMocks
	private EmployeService employeService;
	
	@Mock
	private EmployeRepository employeRepository;
	
	@Test
	public void testEmbaucheEmployeCommercialPleinTempsBTS()
	{
		//Given
		String nom;
		String prenom;
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
		//BDD si l'employé est bien créer (nom, prenom, matricule, salaire, date d'embauche, performance).
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor/*<T>*/.forClass(Employe.class);
		Mockito.verify(employeRepository, Mockito.times(wantedNumberOfInvocations:1)).save(employeArgumentCaptor.getValue());
		Employe employe = employeArgumentCaptor.getValue();
		
		//Employe employeVerif = new Employe(nom,prenom, ...)
		
		Assertions.assertThat(employe.getNom())isEqualTo(nom);
		Assertions.assertThat(employe.getPrenom())isEqualTo(prenom);
		Assertions.assertThat(employe.getNom())isEqualTo("C00346");
		Assertions.assertThat(employe.getNom())isEqualTo(LocalDate.now());
		Assertions.assertThat(employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyMMdd")));
		Assertions.assertThat(employe.getTempsPartiel())isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employe.getPerformance())isEqualTo(1);
		//1521.22*1.2*1.0
		Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
	}
	
	@Test
	public void testEmbaucheEmployeLimiteMatricule() throws EmployeException()
	{
		//Given
		String nom;
		String prenom;
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");
		
		//When
		try
		{
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			Assertion.fail("Aurait du planter !");
		}
		
		catch (Exception e)
		{
			//Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 10000 matricules atteinte !");
		}
		
	}
	
}
