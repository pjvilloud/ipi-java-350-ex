package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

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
	public void testEmbaucheEmploye() throws EmployeException {
	
		//Given
		String nom = "Smith";
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
		//BDD si l'employe est bien cree (nom, prenom, matricule, salaire, date d'embauche, performance, temps partiels)
		
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Employe employe = employeCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        //Calcule Salaire 1521.22 * 1.2 * 170
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
	}
}
