package com.ipiecoles.java.java350.model;



import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.model.Employe;


public class EmployeTest {
	
	// 2 ans avant aujourd'hui => 2 années d'ancienneté 
	
	@Test
	public void testAcnienneteDateEmbaucheNmoins2() {
		
		//Given
		Employe employe=new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));
		
		//when 
		Integer nbAnnee= employe.getNombreAnneeAnciennete();
		
		//Then
		Assertions.assertThat(nbAnnee).isEqualTo(2);	
	}
	
	// Date dans le futur => 0
	
		@Test
		public void testAcnienneteDateEmbaucheNplus2() {
			
			//Given
			Employe employe=new Employe();
			employe.setDateEmbauche(LocalDate.now().plusYears(2));
			
			//when 
			Integer nbAnnee= employe.getNombreAnneeAnciennete();
			
			//Then
			Assertions.assertThat(nbAnnee).isEqualTo(0);	
		}
		// Date aujourd'hui => 0
		
			@Test
			public void testAcnienneteDateEmbaucheAujourdhui() {
				
				//Given
				Employe employe=new Employe();
				employe.setDateEmbauche(null);
				
				//when 
				Integer nbAnnee= employe.getNombreAnneeAnciennete();
				
				//Then
				Assertions.assertThat(nbAnnee).isEqualTo(0);	
			}
		
			// Date NUll => 0
			
				@Test
				public void testAcnienneteDateEmbaucheNull() {
					
					//Given
					Employe employe=new Employe();
					employe.setDateEmbauche(null);
					
					//when 
					Integer nbAnnee= employe.getNombreAnneeAnciennete();
					
					//Then
					Assertions.assertThat(nbAnnee).isEqualTo(0);	
				}
				
				
				//test paramétré 
				@ParameterizedTest(name = "immat {0} est valide : {4}")
				@CsvSource({
				        "'M65222',,2,1,1900",
				        "'T52200',1,3,1,1300 ",
				        "'C12335',4,10,1,5300",
				        "'M54871',,5,1,2200 ",
				        "'T54112',,2,1,1200 ",
				        "'C78522',3,1,1,3400",
				        "'T12345',1,0,1,1000 ",
				        "'T12345',1,0,0.5,500 ", // temp partiel 0.5
				        "'M12345',1,0,1,1700 ",
				        ",1,0,1,1000 ", //matricule null 
				})
				
				public void testCheckPrimeAnnuelle(String immat,Integer performance,Integer NbAnneeAncienite,Double tempPartiel, Double prime) {
					//Given
					Employe employe=new Employe();
					employe.setTempsPartiel(tempPartiel);
					employe.setMatricule(immat);
					employe.setDateEmbauche(LocalDate.now().minusYears(NbAnneeAncienite));
					employe.setPerformance(performance);
					
					//when 
					Double primecalcule=employe.getPrimeAnnuelle();
					
					//Then
				    Assertions.assertThat(primecalcule).isEqualTo(prime);
				}

}
