package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void testGetPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe(
                "Doe",
                "John",
                matricule,
                LocalDate.now().minusYears(nbYearsAnciennete),
                Entreprise.SALAIRE_BASE,
                performance,
                tempsPartiel
        );

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeAnnuelle);
    }
}
