package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
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
    public void augmenterSalaireNormalTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(10);

        //Then
        Assertions.assertEquals(2200d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireFloatTest() throws EmployeException {
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
    public void augmenterSalaireSupPourcentageTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(110);

        //Then
        Assertions.assertEquals(4200d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNegativePourcentageTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(-10);

        //Then
        Assertions.assertEquals(1800d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNullPourcentageTest() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(0);

        //Then
        Assertions.assertEquals(2000d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireWithoutSalaireTest() throws EmployeException {
        //Given
        Employe e = new Employe();

        //When
        e.augmenterSalaire(10);

        //Then
        Assertions.assertEquals(1673.342d, e.getSalaire());
    }

    @Test
    public void augmenterSalaireNullSalaireTest() throws EmployeException{
        //Given
        Employe employe = new Employe("deLaCompta", "Roger", "C00002", LocalDate.now(), null, 3, 7.0 );
        Double pourcentage = 10D;

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
                    //Then
                    employe.augmenterSalaire(pourcentage);
                }
        )//When
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le salaire est null");
    }
}