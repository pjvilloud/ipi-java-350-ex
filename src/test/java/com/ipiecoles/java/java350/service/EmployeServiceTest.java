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
	public void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException{
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.TECHNICIEN;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		//findLastMatricule => 00345 / null
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
		//findByMatricule => null
		Mockito.when(employeRepository.findByMatricule("T00346")).thenReturn(null);
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		//Then
		//BDD si l'employé est bien crée(nom, prénom, matricule, salaire, date d'embauche, performance, temps partiel)
		//Initialisation des capteurs d'argument
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		Employe employe = employeArgumentCaptor.getValue();
		//Employe employeVerif = new Employe(nom, prenom...)
		//Assertions.assertThat(employe).isEqualTo(nom);
		Assertions.assertThat(employe.getNom()).isEqualTo(nom);
		Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
		Assertions.assertThat(employe.getMatricule()).isEqualTo("00346");
		Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
		
		Assertions.assertThat(
				employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
						).isEqualTo(
								LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
						Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
						Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
						Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
						//1521.22 * 1,2 * 1.0
						Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
							
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
                .isInstanceOf(EmployeException.class).hasMessage("Limite des 10000 matricules atteinte !");
		
		//When
		try {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			Assertions.fail("Aurait dû planter !");
			
		} catch (Exception e) {
			// Then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteints");
			
		}
		
		
	}
	
	@Test
	public void testCalculPerformanceCommercialCANull() throws EmployeException{
		//Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		String matricule = "C23455";
		Long caTraite = 0l;
		Long objectifCa = 30000l;
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("C23455");
		
		//When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//Then
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
		Employe employe = employeArgumentCaptor.getValue();
		Assertions.assertThat(employe.getMatricule()).isEqualTo("C23455");
		Assertions.assertThat(employe.getPerformance()).isEqualTo(0);
	
	}

}