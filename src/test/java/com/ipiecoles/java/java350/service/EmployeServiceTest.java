package com.ipiecoles.java.java350.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

//Junit 5
@ExtendWith(value = {MockitoExtension.class})
public class EmployeServiceTest {
	
	@InjectMocks
	private EmployeService employeService;
	@Mock
	private EmployeRepository employeRep;
	
	@Test 
	public void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException{
		
		// Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		// on donne les cas car on n'a pas de BDD donc les données sont apportées avec when
		// cas nominal, findLastMatricule => 00345 / null
		Mockito.when(employeRep.findLastMatricule()).thenReturn("00345");
		// findByMatricule => null / pensez à incrémenter d'1 par rapport au test du dessus 00345, et je veux que ça renvoi null
		Mockito.when(employeRep.findByMatricule("C00346")).thenReturn(null);
		
		// When
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		
		
		// Then
		// BDD (simulée) si l'employé est bien créé (nom, prenom, matricule, salaire, date embauche, performance, temps partiel)
		
		// au moment où on appele save, je récupère les valeur d'employe car je ne peux pas enregistrer en BDD, voir Mock2 cours
		// initialisation des capteurs d'argument
		ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
		
		Mockito.verify(employeRep, Mockito.times(1)).save(employeCaptor.capture());
		// ou 
		// Mockito.verify(employeRep).save(employeCaptor.capture());
		// ou si on n'est pas passé 
		// Mockito.verify(employeRep, Mockito.never).save(employeCaptor.capture());
		
		Employe employe = employeCaptor.getValue();
	
		// Employe employeVerif = new Employe(nom, prenom..);
		// Assertions.assertThat(employe).isEqualTo(employeVerif);
		
		Assertions.assertThat(employe.getNom()).isEqualTo(nom);
		Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
		Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");
		
		Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
		// ou
		//Assertions.assertThat(employeCaptor.getValue().getDateEmbauche().format(
		//		DateTimeFormatter.ofPattern("yyyyMMdd"))).isEqualTo(LocalDate.now().format(
		//				DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
		
		// Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
		
		// salaire de base 1521 * 1.2 * 1.0 = 1825.46
		Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
		
		
	}
	
	@Test 
	public void testEmbaucheEmployeLimiteMatricule() throws EmployeException{
		
		// Given
		String nom = "Doe";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		// je simule que la base arrive à terme
		Mockito.when(employeRep.findLastMatricule()).thenReturn("99999");
		
		// When
		try {
		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		Assertions.fail("Aurait dû planter!"); // fail fait échourer le test qd même et signale que ça aurait du planter
		} catch (Exception e) {
			// Then
			// je fais en sorte que j'ai bien une exception avec le try catch dans when
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}
		
		
		
		
		
		
	}


}
