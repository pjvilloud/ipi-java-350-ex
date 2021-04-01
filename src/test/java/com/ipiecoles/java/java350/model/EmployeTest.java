package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    void testGetNombreAnneeAncienneteWhenDateEmbaucheIsNull() {
        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isZero();
    }

    @Test
    void testGetNombreAnneeAncienneteWhenDateEmbaucheIsSeveralYearsOld() {
        // Given
        int years = 10;
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(years));

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(years);
    }

    @Test
    void testGetNombreAnneeAncienneteWhenDateEmbaucheIsFewMonthsOld() {
        // Given
        int months = 10;
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusMonths(months));

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isZero();
    }

    @Test
    void testGetNombreAnneeAncienneteWhenDateEmbaucheIsNow() {
        // Given
        int years = 10;
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isZero();
    }

    @Test
    void testGetNombreAnneeAncienneteWhenDateEmbaucheIsInFewMonths() {
        // Given
        int months = 10;
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusMonths(months));

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isZero();
    }

    @Test
    void testGetNombreAnneeAncienneteWhenDateEmbaucheIsInYears() {
        // Given
        int years = 10;
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(years));

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isZero();
    }

}
