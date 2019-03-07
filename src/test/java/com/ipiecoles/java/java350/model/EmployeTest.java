package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void augmenterSalaire() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());
        double beforeAg = e.getSalaire();
        //When
        e.augmenterSalaire(beforeAg);
        //Then
        Assertions.assertEquals(beforeAg, (double)e.getSalaire());
        }
    }
