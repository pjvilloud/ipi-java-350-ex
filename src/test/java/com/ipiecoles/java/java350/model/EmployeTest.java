package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void getNombreAnneeAncienneteNow() {
        //given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());
        //when
        Integer nbAnnee = e.getNombreAnneeAnciennete();

        //then
        Assertions.assertEquals( 0, (int)nbAnnee);
    }
}