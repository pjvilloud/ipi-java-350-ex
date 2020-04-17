package java350;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest

class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	void Setup() {
		employeRepository.deleteAll();
	}
	
	 @Test
	    public void testFindLastMatriculeEmpty(){
	        //Given

	        //When
	        String lastMatricule = employeRepository.findLastMatricule();

	        //Then
	        org.junit.jupiter.api.Assertions.assertNull(lastMatricule);
	    }

	    @Test
	    public void testFindLastMatriculeSingle(){
	        //Given
	        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

	        //When
	        String lastMatricule = employeRepository.findLastMatricule();

	        //Then
	        org.junit.jupiter.api.Assertions.assertEquals("12345", lastMatricule);
	    }

	    @Test
	    public void testFindLastMatriculeMultiple(){
	        //Given
	    	employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
	        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
	        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

	        //When
	        String lastMatricule = employeRepository.findLastMatricule();

	        //Then
	        org.junit.jupiter.api.Assertions.assertEquals("40325", lastMatricule);
	    }
	
	
	@Test
	void findLastMatricule0Employe() {
		//given
		
		
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isNull();
		
	}
	
	@Test
	void findLastMatricule3Employe() {
		//given
		Employe e1 = new Employe("Doe", "john", "T12345", LocalDate.now(), 2000d, 1, 1.0);
		Employe e2 = new Employe("Doe", "jane", "C67890", LocalDate.now(), 2000d, 1, 1.0);
		Employe e3 = new Employe("true", "john", "M45678", LocalDate.now(), 2000d, 1, 1.0);
		
		employeRepository.save(e1);
		employeRepository.save(e2);
		employeRepository.save(e3);
		//When
		String lastMatricule = employeRepository.findLastMatricule();
		
		//Then
		Assertions.assertThat(lastMatricule).isEqualTo("67890");
		
	}
	
}