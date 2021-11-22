package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void getNombreAnneeAncienneteWithDateEmbaucheNow() {
        //Given
        Employe employe = new Employe("Doe","John","T12345", LocalDate.now(),25000.0,1,1.0);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteWithDateEmbauchePast() {
        //Given
        Employe employe = new Employe("Doe","John","T12345", LocalDate.now().minusYears(2),25000.0,1,1.0);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test
    void getNombreAnneeAncienneteWithDateEmbaucheFutur() {
        //Given
        Employe employe = new Employe("Doe","John","T12345", LocalDate.now().plusYears(2),25000.0,1,1.0);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteWithNull() {
        //Given
        Employe employe = new Employe("Doe","John","T12345",null,25000.0,1,1.0);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }
}