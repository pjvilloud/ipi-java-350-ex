package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    //#region getNombreAnneeAncienneteTest
    @Test
    void getNombreAnneeAncienneteTest() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }
    @Test
    void getNombreAnneeAncienneteMinusYearTest() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(2);
    }
    @Test
    void getNombreAnneeAnciennetePlusYearTest() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }
    @Test
    void getNombreAnneeAncienneteNullTest() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }
    //#endregion

    //#region getPrimeAnnuelTest
    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'C12345', 2, 0.5, 600.0",
            "1, 'M12345', 1, 0.7, 1260.0"
            })
    void getPrimeAnnuelleTest(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe("Covert",
                "Harry",
                matricule,
                LocalDate.now().minusYears(nbYearsAnciennete),
                Entreprise.SALAIRE_BASE,
                performance,
                tempsPartiel);
        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeAnnuelle);
    }
    //#endregion
}