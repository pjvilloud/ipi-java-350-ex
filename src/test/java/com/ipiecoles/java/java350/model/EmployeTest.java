package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void getNombreAnneeAncienneteWithPastDateEmbauche() {
        //Given
        Employe emplooye = new Employe("Doe", "John", "T12345", LocalDate.now().minusYears(6), 2500.0, 1, 1.0);

        //When
        Integer nbAnnees = emplooye.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteWithFutureDateEmbauche() {
        //Given
        Employe emplooye = new Employe("Doe", "John", "T12345", LocalDate.now(), 2500.0, 1, 1.0);

        //When
        Integer nbAnnees = emplooye.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }
}