package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void augmenterSalaireNormalTest(){
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(10);

        //Then
        Assertions.assertEquals(2200d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireFloatTest(){
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(10.5);

        //Then
        Assertions.assertEquals(2210d, e.getSalaire());
    }

//    @Test
//    public void augmenterSalaireStringTest(){
//        //Given
//        Employe e = new Employe();
//        e.setSalaire(2000d);
//
//        //When
//        e.augmenterSalaire("10");
//
//        //Then
//        Assertions.assertEquals(2200d, e.getSalaire());
//    }

    @Test
    public void augmenterSalaireSupPourcentageTest(){
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(110);

        //Then
        Assertions.assertEquals(4200d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNegativePourcentageTest(){
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(-10);

        //Then
        Assertions.assertEquals(1800d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNullPourcentageTest(){
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When :
        e.augmenterSalaire(0);

        //Then
        Assertions.assertEquals(2000d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireWithoutSalaireTest(){
        //Given
        Employe e = new Employe();

        //When
        e.augmenterSalaire(10);

        //Then
        Assertions.assertEquals(1673.342d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNullSalaireTest(){
        //Given
        Employe e = new Employe();
        e.setSalaire(null);

        //When
        e.augmenterSalaire(10);

        //Then :
        Assertions.assertNull(e.getSalaire());
    }
}