package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    @Test
    void testNbAnneeAncienneteNow() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isZero();
    }

    @Test
    void testNbAnneeAncienneteNowMoins2() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test
    void testNbAnneeAncienneteNowPlus3() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isNull();
    }

    @Test
    void testNbAnneeAncienneteNull() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isNull();
    }

    @Test
    void testPrimeAnnuelleUnitaire() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("C12345");
        employe.setTempsPartiel(1.0);
        employe.setDateEmbauche(LocalDate.now());
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(1000d);
    }


    @ParameterizedTest
    @CsvSource({
            "'C12345', 1.0, 0, 1, 1000.0",
            "'M12345', 1.0, 0, 1, 1700.0",
            "'C12345', 1.0, 2, 2, 2500.0"
    })
    void testGetPrimeAnnuelle(String matricule, Double tempsPartiel, Integer nbAnneeAnciennete, Integer performance, Double primeCalculee) {
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setTempsPartiel(tempsPartiel);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setPerformance(performance);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeCalculee);
    }
}
