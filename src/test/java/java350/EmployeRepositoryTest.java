package java350;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@SpringBootConfiguration
class EmployeRepositoryTest {
	
	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	void Setup() {
		employeRepository.deleteAll();
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