package com.ipiecoles.java.java350.unit;

import java.io.ByteArrayInputStream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ipiecoles.java.java350.CompteEpargne;
import com.ipiecoles.java.java350.utils.TestUtils;

public class CompteEpargneTest {

	
	
	@Test
	public void testCompteEpargne(){
		
		//Given
		 String input =  "123" + TestUtils.LINE_SEPARATOR + "500" + TestUtils.LINE_SEPARATOR + "200"+ TestUtils.LINE_SEPARATOR ;
	        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
	        System.setIn(inContent);
		//When
		CompteEpargne ce = new CompteEpargne();
		//Then
		Assertions.assertThat(ce.getTypeCompte()).isEqualTo("Epargne");
		
	}
	
/*	@Test
	public void testControleTauxInfAZero(){
		
		//Given
		 String input = "123" + TestUtils.LINE_SEPARATOR + "500" + TestUtils.LINE_SEPARATOR + "200" + TestUtils.LINE_SEPARATOR ;
	        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
	        System.setIn(inContent);
		//When
		CompteEpargne ce = new CompteEpargne();
		//Then
		Assertions.assertThat(ce.getTypeCompte()).isEqualTo("EPARGNE");
		
	}*/
}
