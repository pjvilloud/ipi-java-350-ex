package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

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
    @ParameterizedTest
    @CsvSource({
            "'M12345',0,1,1.0,1700.0"
    })
    void getPrimeAnnuelle (String Matricule, Integer nbAnneeAnciennete, Integer performance, Double TempsPartiel, Double PrimeCalculee) {
        //Given
        //Matricule, Date embauche,
        Employe employe = new Employe("Doe", "Joe", "M12345", LocalDate.now(), 2500.0, 1,1.0);
        //When
        Double prime = employe.getPrimeAnnuelle();
        //Then
        Assertions.assertThat(prime).isEqualTo(PrimeCalculee);
    }

}