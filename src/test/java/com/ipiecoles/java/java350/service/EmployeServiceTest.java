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

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {
	@InjectMocks
    public EmployeService employeService;
    @Mock
    public EmployeRepository employeRepository;
    @Test
    public void testFindByImmatNotFound() throws EntityExistsException, EmployeException{
    	
    // Given 
    	String prenom="Nasim";
    	String nom="Ibrahimi";
    	Poste poste=Poste.COMMERCIAL;
    	NiveauEtude niveauEtude=NiveauEtude.BTS_IUT;
    	Double tempsPartiel=1.0;
  
    	//findLastMatrcule => 00345 /null
    	Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
    	//Mockito.when(employeRepository.findLastMatricule()).thenCallRealMethod();
    	//then call real method is for calling the real method an get the value on this call instead of giving a value 
    	
    	//findByMatricule => null 
    	Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null); // l'id après celui qu'on donné dans la prmière parite (le suivant) 
    
    	
    // When 
    	employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
    	
    //Then 
    	//après l'initialisation de l'employé et sa création on 
    	ArgumentCaptor<Employe> employeCaptor=ArgumentCaptor.forClass(Employe.class); // on peut prendre la valeur ou l'argument d'une méthode
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture()); //ici on regrade: Est ce que la méthode sava de l'employeRepository a été appelée au moin une seule fois?
        // pour la calsse Employé en mettant en paramètre la variable employeCaptor de la classe Employe// le pramètre time n'est pas obligatoire // mokito.never() est pour vérifier est ce qu'on est dans un bloc d'if ou ect 
        Employe employe=employeCaptor.getValue();
      
//        Employe employeVerif=new Employe(nom, prenom, poste, niveauEtude, tempsPartiel);
//        Assertions.assertThat(employe).isEqualTo(employeVerif);
        
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00346");
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employeCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd"))).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        // 15121.22 *1.2*170   le calcule de salaire 
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);

	  }
    
    
    @Test
    public void testLimteMatricule() throws EntityExistsException, EmployeException{
    	
    // Given 
    	String prenom="Nasim";
    	String nom="Ibrahimi";
    	Poste poste=Poste.COMMERCIAL;
    	NiveauEtude niveauEtude=NiveauEtude.BTS_IUT;
    	Double tempsPartiel=1.0;
  
    	//findLastMatrcule => 00345 /null
    	Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999"); // when this meéthod est applé retourne comme value 99999
    	//Mockito.when(employeRepository.findLastMatricule()).thenCallRealMethod();
    	//then call real method is for calling the real method an get the value on this call instead of giving a value 
    	
    		// When 
    	try{	
    		employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
    		Assertions.fail("Aurait du planter");// if the exception is not execute this message tell as that the test is not executed alors que ca dervait être executé 
    	}catch(Exception ex) {
    		//Then verification du lancement de l'exeption 
    		Assertions.assertThat(ex).isInstanceOf(EmployeException.class); //verify que la classe d'exption est bien celle donnée 
    		Assertions.assertThat(ex.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
    	}
    
    	//la version simpligiée de try catch 
       	Assertions.assertThatThrownBy(()->{employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);})
    	.isInstanceOf(EmployeException.class)
    	.hasMessage("Limite des 100000 matricules atteinte !");

	  }
	}




