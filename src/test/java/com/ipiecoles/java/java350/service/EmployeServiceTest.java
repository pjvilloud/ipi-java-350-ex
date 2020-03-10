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
	public void embaucheEmployeTest() throws EmployeException {
		// Given
		String nom= "Dao";
		String prenom = "John";
		Poste poste=Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00356");
		Mockito.when(employeRepository.findByMatricule("C00357")).thenReturn(null);
		
		
		// When 
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		// Then 
		Employe employe= new Employe(nom, prenom, "C00357", LocalDate.now(), 1825.46, Entreprise.PERFORMANCE_BASE, 1.0);
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository,Mockito.times(1)).save(employeCaptor.capture());
		Assertions.assertThat(employeCaptor.getValue()).isEqualTo(employe);
		
		
		
	}
	
	@Test
	public void embaucheEmployeTestNull() throws EmployeException {
		// Given
		String nom= "Dao";
		String prenom = "John";
		Poste poste=Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
		
		
		// When 
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		// Then 
		Employe employe= new Employe(nom, prenom, "C00001", LocalDate.now(), 1825.46, Entreprise.PERFORMANCE_BASE, 1.0);
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(employeRepository,Mockito.times(1)).save(employeCaptor.capture());
		Assertions.assertThat(employeCaptor.getValue()).isEqualTo(employe);
		
		
		
	}

}
