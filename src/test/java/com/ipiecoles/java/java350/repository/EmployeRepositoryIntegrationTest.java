package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryIntegrationTest {

	@Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }
    
    /*
     * Test cas d'entrée null
     */
    @Test
    public void integrationAvgPerformanceWhereMatriculeStartsWithParamInvalid() {
    	Assertions.assertNull(employeRepository.avgPerformanceWhereMatriculeStartsWith(null));
    }
    
    @Test
    public void integrationAvgPerformanceWhereMatriculeStartsWith() {
    	//Given
    	String nom = "Doe";
    	String prenom = "John";
    	employeRepository.save(new Employe(nom, prenom, "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
    	employeRepository.save(new Employe(nom, prenom, "M00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
    	employeRepository.save(new Employe(nom, prenom, "T00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 0, 1.0));
    	
    	//When
    	Double AvgPerformanceCommercial = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
    	Double AvgPerformanceManager = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
    	Double AvgPerformanceTechnicien = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");
    	
    	//Then
    	Assertions.assertEquals(1, (double)AvgPerformanceCommercial);
    	Assertions.assertEquals(2, (double)AvgPerformanceManager);
    	Assertions.assertEquals(0, (double)AvgPerformanceTechnicien);
    }
}
