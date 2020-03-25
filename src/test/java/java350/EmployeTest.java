package java350;

import java.time.LocalDate;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ipiecoles.java.java350.model.Employe;

public class EmployeTest {
	
	@Test
	public void testS1() {
		
		//GIVEN
		Employe employe= new Employe();
		employe.setDateEmbauche(LocalDate.now());
		//When
		Integer nbAnnees = employe.getNombreAnneeAnciennete();
		//THEN
		org.assertj.core.api.Assertions.assertThat(nbAnnees).isEqualTo(0);
	}
	
	@Test
	public void testS2() {
		
		//GIVEN
		
		//When
		
		//THEN
		
	}
	

}
