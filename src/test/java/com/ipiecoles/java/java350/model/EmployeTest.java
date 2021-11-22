package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void getNombreAnneeAncienneteWithDateEmbaucheNow() {
        //Given
        Employe employe = new Employe("Doe","John","T12345",localDate.now(),25000.0,1,1.0);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }
}