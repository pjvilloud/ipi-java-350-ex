package com.ipiecoles.java.java350.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testNow(){
        //Given
        Employe emp = new Employe();
        emp.setDateEmbauche(LocalDate.now());

        //When
        Integer res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, res);
    }

    @Test
    public void testMinus(){
        //Given
        Employe emp = new Employe();
        emp.setDateEmbauche(LocalDate.now().minusYears(1));

        //When
        Integer res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(1, res);
    }

    @Test
    public void testPlus(){
        Employe emp = new Employe();
        emp.setDateEmbauche(LocalDate.now().plusYears(1));

        //When
        Integer res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, res);
    }
}
