package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
	
	//Test paramétrés
	//matricule, performance, date d'embauche, temps partiel, prime
		
		@ParameterizedTest(name = "perf {0} est valide : {1}")
		@CsvSource({
		        
		        "'C00002', 1, 'C00003', 1, 'C00004', 1, 1, 25000, 100000, 1 " ,
				"'C00005', 2, 'C00006', 2, 'C00007', 2, 2, 93000, 100000, 1 " ,
				"'C00008', 3, 'C00009', 3, 'C00010', 3, 3, 102000, 100000, 3 ",
				"'C00011', 3, 'C00012', 3, 'C00013', 3, 3, 110000, 100000, 5 ",
				"'C00014', 1, 'C00015', 1, 'C00016',1, 1, 121000, 100000, 6 ",
				"'C00017', 1, 'C00018', 1, 'C00019', 1, 1, 80000, 100000, 1 "
			
		})
	public void testcalculPerformanceCommercial(String matriculEmp1, Integer performanceEmp1,
			String matriculEmp2, Integer performanceEmp2,
			String matriculEmp3, Integer performanceEmp3,
			Double average, Long caTraite, Long caObjectif, Integer performanceCalc) throws EmployeException{
		
	//Given
		Employe emp1 = new Employe();
		emp1.setMatricule(matriculEmp1);
		emp1.setPerformance(performanceEmp1);
		Employe emp2 = new Employe();
		emp2.setMatricule(matriculEmp2);
		emp2.setPerformance(performanceEmp2);
		Employe emp3 = new Employe();
		emp3.setMatricule(matriculEmp3);
		emp3.setPerformance(performanceEmp3);
		
		//findLastMatricule => "00345"
		Mockito.when(employeRep.findByMatricule(matriculEmp1)).thenReturn(emp1);
		
		//findByMatricule => null
		Mockito.when(employeRep.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(average);
		
	//When
		 employeService.calculPerformanceCommercial(matriculEmp1, caTraite, caObjectif);
		 	
	//Then
		 ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
	        Mockito.verify(employeRep, Mockito.times(1)).save(employeCaptor.capture());
	        emp1 = employeCaptor.getValue();
	        Assertions.assertThat(emp1.getPerformance()).isEqualTo(performanceCalc);	
		
		
	}
	
		@Test
	    public void testCalculePerformanceCtraitNull() {
	        Assertions.assertThatThrownBy(() -> {
	            employeService.calculPerformanceCommercial("C21235", null, 150000L);
	        }).isInstanceOf(EmployeException.class)
	                .hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
	    }	
		
		@Test
	    public void testCalculePerformanceMoinsZero() {
			Assertions.assertThatThrownBy(() -> {
	            employeService.calculPerformanceCommercial("C21235", -1L, 150000L);
	        }).isInstanceOf(EmployeException.class)
	                .hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
	    }	
		
		@Test
	    public void testCalculePerformanceObjectifCaNull() {
	        Assertions.assertThatThrownBy(() -> {
	            employeService.calculPerformanceCommercial("C21235", 25000L, null);
	        }).isInstanceOf(EmployeException.class)
	                .hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
	    }
		
		@Test
	    public void testCalculePerformanceObjectifCMoinsZero() {
	        Assertions.assertThatThrownBy(() -> {
	            employeService.calculPerformanceCommercial("C21235", 25000L, -1L);
	        }).isInstanceOf(EmployeException.class)
	                .hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
	    }

}
