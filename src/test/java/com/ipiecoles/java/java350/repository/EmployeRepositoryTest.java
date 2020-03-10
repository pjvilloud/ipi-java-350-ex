package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;


@SpringBootTest
public class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	@Test
	public void testFindLastMatricule0Emp() {
		
		//Given
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isNull();
	}
	
	@Test
	public void testFindLastMatricule2Emp() {
		
		//Given
		Employe e1 = new Employe("Doe", "Jane", "M99999", LocalDate.now(), 1500d, 1, 1.0);
		employeRepository.save(e1);
		
		Employe e2 = new Employe("Doe", "John", "T99998", LocalDate.now(), 1500d, 1, 1.0);
		employeRepository.save(e2);
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isEqualTo("99999"); 
	}
	
	@Test
    public void testAvgPerformanceWhereMatriculeStartsWithC(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "C12346", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        employeRepository.save(new Employe("Doe", "Jenny", "C12347", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));

        //When
        Double avgPerformanceC = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat((double) avgPerformanceC).isEqualTo(2.0);
    }


    @Test
    public void testAvgPerformanceWhereMatriculeStartsWith(){
        //Given

        //When
        Double avgPerformanceT = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");
        Double avgPerformanceC = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        Double avgPerformanceM = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");

        //Then
        Assertions.assertThat(avgPerformanceT).isNull();;
        Assertions.assertThat(avgPerformanceC).isNull();;
        Assertions.assertThat(avgPerformanceM).isNull();;

    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithCorM(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M45678", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jenny", "C12346", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        Double avgPerformanceT = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");
        Double avgPerformanceC = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        Double avgPerformanceM = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");

        //Then
        Assertions.assertThat((double) avgPerformanceC).isEqualTo(1.0);
        Assertions.assertThat((double) avgPerformanceM).isEqualTo(1.0);
        Assertions.assertThat(avgPerformanceT).isNull();;
    }
	

}
