package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;

//For mock test
@ExtendWith(MockitoExtension.class)

// For integrated test
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceTest {
	
	// For integrated test
	@Autowired
	private EmployeRepository empRepo;
	
	@Autowired
	private EmployeService empService;
	
	@BeforeEach
    @AfterEach
    public void setup() {
    	empRepo.deleteAll();
    }
	
	// For mock test
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
	
	@Test
	public void testEmbaucheEmployeLimiteMatricule() throws EmployeException {
	
		//Given
		String nom = "Smith";
		String prenom = "John";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");
		
		//example with lambda notation (does the same thing)
		//when/then Assertj
//		Assertions.assertThatThrownBy(() ->	{employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);})
//		.isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");
		
		
		//When
		try {
			employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
			Assertions.fail("Aurait du planter !");
		} catch(Exception e) {
			//then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}
	}
	
/* ------------------------  	Tester sans dépendance à la BDD la méthode calculPerformanceCommercial d'EmployeService ------------------------ */	
	
	@Test
	public void testCalculPerformanceCommercialAvgPerf() throws EmployeException {
		
//		Si la performance calculée est supérieure à la moyenne des performances des commerciaux, le commercial reçoit + 1 de performance.
		
		//Given
		String matricule = "C01234";
		Long caTraite = 100l;
		Long objectifCa = 100l;
		Integer performance = 3;
		
		Employe myEmploye = new Employe();
		myEmploye.setMatricule(matricule);
		myEmploye.setPerformance(performance);
		
		Mockito.when(employeRepository.findByMatricule("C01234")).thenReturn(myEmploye);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1d);
		
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//then
		ArgumentCaptor<Employe> myEmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(myEmployeCaptor.capture());
        Employe employe = myEmployeCaptor.getValue();
//		Performance initial 3, performance moyenne 1 donc le commercial reçoit +1 => 4  
        Assertions.assertThat(employe.getPerformance()).isEqualTo(4); 	
	}
	
	
	@Test
	public void testCalculPerformanceCommercialInf20p() throws EmployeException {
		
//		1 : Si le chiffre d'affaire est inférieur de plus de 20% à l'objectif fixé, le commercial retombe à la performance de base => 1
		
		//Given
		String matricule = "C01234";
		Long caTraite = 15l;
		Long objectifCa = 100l;
		Integer performance = 3;
		
		Employe myEmploye = new Employe();
		myEmploye.setMatricule(matricule);
		myEmploye.setPerformance(performance);
		
		Mockito.when(employeRepository.findByMatricule("C01234")).thenReturn(myEmploye);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5d);
		
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//then
		ArgumentCaptor<Employe> myEmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(myEmployeCaptor.capture());
        Employe employe = myEmployeCaptor.getValue();
//		Performance initial 3 mais le commercial retombe à la performance de base => 1  
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1); 	
	}
	
	
	@Test
	public void testCalculPerformanceCommercialInf20a5p() throws EmployeException {
		
//		2 : Si le chiffre d'affaire est inférieur entre 20% et 5% par rapport à l'ojectif fixé, il perd 2 de performance (dans la limite de la performance de base)
		
		//Given
		String matricule = "C01234";
		Long caTraite = 85l;
		Long objectifCa = 100l;
		Integer performance = 5;
		
		Employe myEmploye = new Employe();
		myEmploye.setMatricule(matricule);
		myEmploye.setPerformance(performance);
		
		Mockito.when(employeRepository.findByMatricule("C01234")).thenReturn(myEmploye);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(6d);
		
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//then
		ArgumentCaptor<Employe> myEmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(myEmployeCaptor.capture());
        Employe employe = myEmployeCaptor.getValue();
//		Performance initial 5 mais le commercial perd 2 de performance => 3  
        Assertions.assertThat(employe.getPerformance()).isEqualTo(3); 	
	}
	
	
	@Test
	public void testCalculPerformanceCommercial() throws EmployeException {
		
//		3 : Si le chiffre d'affaire est entre -5% et +5% de l'objectif fixé, la performance reste la même.
		
		//Given
		String matricule = "C01234";
		Long caTraite = 100l;
		Long objectifCa = 100l;
		Integer performance = 1;
		
		Employe myEmploye = new Employe();
		myEmploye.setMatricule(matricule);
		myEmploye.setPerformance(performance);
		
		Mockito.when(employeRepository.findByMatricule("C01234")).thenReturn(myEmploye);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5d);
		
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//then
		ArgumentCaptor<Employe> myEmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(myEmployeCaptor.capture());
        Employe employe = myEmployeCaptor.getValue();
//		Performance initial 1 mais la performance reste la même => 1  
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1); 	
	}
	
	
	@Test
	public void testCalculPerformanceCommercialSup5a20p() throws EmployeException {
		
//		4 : Si le chiffre d'affaire est supérieur entre 5 et 20%, il gagne 1 de performance
		
		//Given
		String matricule = "C01234";
		Long caTraite = 110l;
		Long objectifCa = 100l;
		Integer performance = 1;
		
		Employe myEmploye = new Employe();
		myEmploye.setMatricule(matricule);
		myEmploye.setPerformance(performance);
		
		Mockito.when(employeRepository.findByMatricule("C01234")).thenReturn(myEmploye);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5d);
		
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//then
		ArgumentCaptor<Employe> myEmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(myEmployeCaptor.capture());
        Employe employe = myEmployeCaptor.getValue();
//		Performance initial 1 mais il gagne 1 de performance => 2  
        Assertions.assertThat(employe.getPerformance()).isEqualTo(2); 	
	}
	
	
	@Test
	public void testCalculPerformanceCommercialSup20p() throws EmployeException {
		
//		5 : Si le chiffre d'affaire est supérieur de plus de 20%, il gagne 4 de performance
		
		//Given
		String matricule = "C01234";
		Long caTraite = 140l;
		Long objectifCa = 100l;
		Integer performance = 1;
		
		Employe myEmploye = new Employe();
		myEmploye.setMatricule(matricule);
		myEmploye.setPerformance(performance);
		
		Mockito.when(employeRepository.findByMatricule("C01234")).thenReturn(myEmploye);
		Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(5d);
		
		
		//When
		employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
		
		//then
		ArgumentCaptor<Employe> myEmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(myEmployeCaptor.capture());
        Employe employe = myEmployeCaptor.getValue();
//		Performance initial 1 mais il gagne 4 de performance => 5  
        Assertions.assertThat(employe.getPerformance()).isEqualTo(5); 	
	}
	

/* ------------------------  	Tester de manière intégrée une cas nominal de la méthode calculPerformanceCommercial d'EmployeService ------------------------ */
	
	@Test
    public void testCalculCommercialPerformance() throws EmployeException {
    	//Given
		
		String matricule = "C01234";
		Long caTraite = 140l;
		Long objectifCa = 100l;
		Integer performance = 3;
		
		Employe myEmploye = empRepo.save(new Employe("doe","john", matricule, LocalDate.now(), 1500d, performance, 1.0));
		Employe e1 = empRepo.save(new Employe("Smith","john","C01111", LocalDate.now(), 1600d, 1, 1.1));
		Employe e2 = empRepo.save(new Employe("Park","john","C11112", LocalDate.now(), 1600d, 2, 1.1));
		//When
		empService.calculPerformanceCommercial(matricule, caTraite, objectifCa);    	
		
        //Then
		Employe emp = empRepo.findByMatricule(matricule);
//		Performance initial 3. Chiffre d'affaire est supérieur de plus de 20%, donc + 4 de performance. Enfin la moyenne de perf est de (3+1+2)/3 = 2 donc +1. Nouvelle performance => 8 
		Assertions.assertThat(emp.getPerformance()).isEqualTo(8);
    }
}
