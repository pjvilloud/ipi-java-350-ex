package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ipiecoles.java.java350.model.Employe;

@SpringBootTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	/**
	 * Méthode qui réinitilise la BDD avant de lancer chaque test
	 */
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	/**
	 * Test de la méthode findLastMatricule
	 * avec 0 employé
	 */
	@Test
    public void testFindLastMatricule0Employe(){
        //Given
        //When
        String result = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(result).isNull(); 
    }
	
	/**
	 * Test de la méthode findLastMatricule
	 * avec 3 employés
	 */
	@Test
    public void testFindLastMatricule3Employes(){
        //Given
        Employe e1 = employeRepository.save(new Employe("Christophe", "LIMAO", "C88888", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e2 = employeRepository.save(new Employe("Christophe", "LIMAO", "C99995", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e3 = employeRepository.save(new Employe("Christophe", "LIMAO", "T77777", LocalDate.now(), 2500.0, 5, 1.0));
        //When
        String result2 = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(result2).isEqualTo("99995"); 
    }
	
	/**
	 * Test de la méthode avgPerformanceWhereMatriculeStartsWith
	 * avec des Managers
	 */
	@Test
    public void testAvgPerformanceWhereMatriculeStartsWithM(){
        //Given
        Employe e1 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00001", LocalDate.now(), 2500.0, 5, 1.0));
        Employe e2 = employeRepository.save(new Employe("Christophe", "LIMAO", "C99999", LocalDate.now(), 2500.0, 8, 1.0));
        Employe e3 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00002", LocalDate.now(), 2500.0, 7, 1.0));
        Employe e4 = employeRepository.save(new Employe("Christophe", "LIMAO", "T88888", LocalDate.now(), 2500.0, 1, 1.0));
        Employe e5 = employeRepository.save(new Employe("Christophe", "LIMAO", "M00003", LocalDate.now(), 2500.0, 9, 1.0));
        Employe e6 = employeRepository.save(new Employe("Christophe", "LIMAO", "C33333", LocalDate.now(), 2500.0, 2, 1.0));
        //When
        Double result3 = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");
        //Then
        Assertions.assertThat(result3).isEqualTo(7); 
    }
	
	/**
	 * Test de la méthode avgPerformanceWhereMatriculeStartsWith
	 * avec des Commerciaux dont une des valeurs est nulle
	 */
	@Test
    public void testAvgPerformanceWhereMatriculeStartsWithCavecValeurNulle(){
        //Given
        Employe e1 = employeRepository.save(new Employe("Christophe", "LIMAO", "C00001", LocalDate.now(), 2500.0, 4, 1.0));
        Employe e2 = employeRepository.save(new Employe("Christophe", "LIMAO", "C99999", LocalDate.now(), 2500.0, null, 1.0));
        Employe e3 = employeRepository.save(new Employe("Christophe", "LIMAO", "C00002", LocalDate.now(), 2500.0, 2, 1.0));
        //When
        Double result4 = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        //Then
        Assertions.assertThat(result4).isEqualTo(3);
    }
	
}
