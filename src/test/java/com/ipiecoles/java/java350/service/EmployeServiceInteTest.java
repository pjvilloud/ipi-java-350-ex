package com.ipiecoles.java.java350.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@SpringBootTest
public class EmployeServiceInteTest {

	@Autowired
	public EmployeService employeService;
	
	@Autowired
	private EmployeRepository employeRepository;
	
	@BeforeEach
    public void setUpDB() {
        employeRepository.deleteAll();
    }
	
	
	// ---------------- EVALUATION INTEGRATION-----------------------------
	
	// cas nominal 
	@Test
	public void testCalculPerformanceCommercialInte() throws EmployeException {
		
		// given
		Employe employe = new Employe();
		employe.setNom("Doe");
		employe.setPrenom("John");
		employe.setMatricule("C12345");
		employe.setDateEmbauche(LocalDate.now());
		employe.setSalaire(1000D);
		employe.setPerformance(1);
		employe.setTempsPartiel(1.0);
		Long caTraite = 1000L;
		Long objectifCa = 1000L;
        employeRepository.save(employe);
		
        // when
        employeService.calculPerformanceCommercial(employe.getMatricule(), caTraite, objectifCa);
        Employe empl = employeRepository.findByMatricule("C12345");
        
        // then
        Assertions.assertThat(empl.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        
	}
	
	
	// cas avec avgPerformanceWhereMatriculeStartsWith 
		@Test
		public void testCalculPerformanceCommercialMoyenneInte() throws EmployeException {
			
			// given
			Employe employe = new Employe();
			employe.setNom("Doe");
			employe.setPrenom("John");
			employe.setMatricule("C12345");
			employe.setDateEmbauche(LocalDate.now());
			employe.setSalaire(1000D);
			employe.setPerformance(1);
			employe.setTempsPartiel(1.0);
			Long caTraite = 1000L;
			Long objectifCa = 1000L;
	        employeRepository.save(employe);
			
	        // when
	        employeService.calculPerformanceCommercial(employe.getMatricule(), caTraite, objectifCa);
	        Double moyenneC = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
	        
	        // then
	        Assertions.assertThat(moyenneC).isEqualTo(Entreprise.PERFORMANCE_BASE.doubleValue());
	        
		}
	
	
	
}






