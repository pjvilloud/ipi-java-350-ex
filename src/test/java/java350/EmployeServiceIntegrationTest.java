package java350;

import java.time.LocalDate;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;

@SpringBootTest
public class EmployeServiceIntegrationTest {
	
	@Autowired
	EmployeService employeService;
	
	@Autowired
	EmployeRepository employeRepository;
	
	
	@BeforeEach
	void  setup() {
		employeRepository.deleteAll();
	}
	
	
	@Test
	void testEmbaucheEmploye() {
		//given
		//Employe employe = new Employe("doe", "jane", "C12345", LocalDate.now(), 2000d);
		//employeRepository.save(employeInitial);
		//When
		
		//Then
	}
}