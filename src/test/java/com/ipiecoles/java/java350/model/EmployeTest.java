package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void testGetNombreAnneeAncienneteDateEmbaucheNow() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());
        //When
        Integer nbAnneeAnciennete= e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertEquals(0, (int)nbAnneeAnciennete);
    }

    @Test
    void testGetNombreAnneeAncienneteDateEmbaucheFutur() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));
        //When
        Integer nbAnneeAnciennete= e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertEquals(0, (int)nbAnneeAnciennete);
    }

    @Test
    void testGetNombreAnneeAncienneteDateEmbaucheMinus2Years() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));
        //When
        Integer nbAnneeAnciennete= e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertEquals(2, (int)nbAnneeAnciennete);
    }

    @Test
    void testGetNombreAnneeAncienneteDateEmbaucheNull() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);
        //When
        Integer nbAnneeAnciennete= e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertEquals(0, (int)nbAnneeAnciennete);
    }
}