package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

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
	private EmployeRepository employeRep;
	
	@Test
	public void testEmbaucheEmployeIfExist() throws EmployeException{
		
	//Given
		String nom ="Doe";
		String prenom = "Jean";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
		
		//findLastMatricule => "00345"
		Mockito.when(employeRep.findLastMatricule()).thenReturn("00345");
		
		//findByMatricule => null
		Mockito.when(employeRep.findByMatricule("C00346")).thenReturn(null);
		
	//When
		 employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		 
				
	//Then
		 
		 
		 ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
	        Mockito.verify(employeRep, Mockito.times(1)).save(employeCaptor.capture());
	        Employe employe = employeCaptor.getValue();
	        
	        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
	        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
	        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");
	        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
	        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
	        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
	        //one below has a same result as Entreprise.PERFORMANCE_BASE 
	        //Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
	        //1521.22 * 1.2 * 1.0
	        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
	        
	        //Mockito.verify(employeRep, Mockito.times(1)).save(employeCaptor.capture());
	        //Assertions.assertThat(employeCaptor.getValue().getNom()).isEqualTo("Doe");
	        //Assertions.assertThat(employeCaptor.getValue().getPrenom()).isEqualTo("Jean");
	        //Assertions.assertThat(employeCaptor.getValue().getTempsPartiel()).isEqualTo(1.0);
	        //Assertions.assertThat(employeCaptor.getValue().getMatricule()).isEqualTo("C00346");		        
		
		
	}
	
	@Test
	public void testEmbaucheEmployeLimiteMatricule() throws EmployeException{
		
	//Given
		String nom ="Doe";
		String prenom = "Jean";
		Poste poste = Poste.COMMERCIAL;
		NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
		Double tempsPartiel = 1.0;
	
		Mockito.when(employeRep.findLastMatricule()).thenReturn("99999");
		
	//When/Then AssertJ (This does same job as the one below with TRY and CATCH)
		Assertions.assertThatThrownBy(()-> {employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);})
		.isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");
		
	//When
		try {
		 employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
		 Assertions.fail("Aurait du planter!");
		}catch(Exception e) {
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
			
		}
				
	//Then
		 
		
	}

}
