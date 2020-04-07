package com.ipiecoles.java.java350;

import java.time.LocalDate;

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
class EmployeServiceTest {
	
	@InjectMocks
	EmployeService employeService;
	
	@Mock
	EmployeRepository employeRepository;
	
	//@BeforeEach
	//MockitoAnnotations.
	
	@Test
	void embaucheEmploye() throws EmployeException {
		//given (qd la methode findlastmatricule va etre appelee on veut qu'elle renvoie null pour 
		//simuler une base de donnée employe vide
		Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
		//qd on va chercher si l'employé ac le matricule calculé existe, on veut que la methode renvoie null
		Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
		
		//WHEN
		employeService.embaucheEmploye("doe", "john", Poste.COMMERCIAL, NiveauEtude.LICENCE, 1.0);
		
		//Then
		
	}
	
	@Test
	void embaucheEmployeXEmploye() throws EmployeException {
		//given (qd la methode findlastmatricule va etre appelee on veut qu'elle renvoie null pour 
		//simuler une base de donnée employe vide
		Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");
		//qd on va chercher si l'employé ac le matricule calculé existe, on veut que la methode renvoie null
		Mockito.when(employeRepository.findByMatricule("M45679")).thenReturn(null);
		//peut definir variable pour assertions
		Poste poste = Poste.MANAGER;
		
		//WHEN
		employeService.embaucheEmploye("doe", "john", poste, NiveauEtude.LICENCE, 1.0);
		
		
		
		//Then
		//Employe employe = employeRepository.findByMatricule("M45679");
		ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
		//on vérifie qu ela methode save a bien été appelée sue employeRepository, et on capture le parametre
		Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
		Employe employe = employeArgumentCaptor.getValue();
		Assertions.assertThat(employe.getNom()).isEqualTo("doe");
		Assertions.assertThat(employe.getPrenom()).isEqualTo("john");
		Assertions.assertThat(employe.getMatricule()).isEqualTo("M45679");
		Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
		Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
		
		//calcule du salaire
		Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
		
	}
	
	void testExceptionNormal(){
	    //Given
	    try {
	        //When
	        employeService.embaucheEmploye(null, null, null, null, null);
	        Assertions.fail("Aurait du lancer une exception");
	    } catch (Exception e){
	        //Then
	        //Vérifie que l'exception levée est de type EmployeException
	        Assertions.assertThat(e).isInstanceOf(EmployeException.class);
	        //Vérifie le contenu du message
	        Assertions.assertThat(e.getMessage()).isEqualTo("Message de l'erreur");
	    }
	}
	//gerer test exception methode2
	void testExceptionJava8(){
	    //Given
	    Assertions.assertThatThrownBy(() -> {
	        //When
	        employeService.embaucheEmploye(null, null, null, null, null);
	    })//Then
	            .isInstanceOf(EmployeException.class).hasMessage("Message de l'erreur");
	}
	
}