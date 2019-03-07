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
        e.setSalaire(0.0);
        Double beforeAg = e.getSalaire();
        //When
        if ((beforeAg != null) && (beforeAg > 0)){
            e.augmenterSalaire(beforeAg);
        }
        else {
            System.out.println("Oups ! Une erreur est survenue");
        }
        //Then
        Assertions.assertEquals(beforeAg, e.getSalaire());
        }
    }
