package com.ipiecoles.java.java350.model;

import com.google.protobuf.DoubleValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        com.ipiecoles.java.java350.model.Employe e = new com.ipiecoles.java.java350.model.Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteMinus2(){
        //Given
        com.ipiecoles.java.java350.model.Employe e = new com.ipiecoles.java.java350.model.Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull() {
        //Given
        com.ipiecoles.java.java350.model.Employe e = new com.ipiecoles.java.java350.model.Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2() {
        //Given
        com.ipiecoles.java.java350.model.Employe e = new com.ipiecoles.java.java350.model.Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
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
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        com.ipiecoles.java.java350.model.Employe employe = new com.ipiecoles.java.java350.model.Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), com.ipiecoles.java.java350.model.Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);

    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getTempsPartielA05(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        com.ipiecoles.java.java350.model.Employe employe = new com.ipiecoles.java.java350.model.Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), com.ipiecoles.java.java350.model.Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        employe.setTempsPartiel(0.5d);
        Double prime = employe.getPrimeAnnuelle();

        //Then
        org.assertj.core.api.Assertions.assertThat(employe.getTempsPartiel()).isLessThan(1d);
        Assertions.assertEquals(primeAnnuelle/2, prime);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 0.5, 500.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "2, 'T12345', 0, 0.5, 1150.0",
            "2, 'T12345', 1, 0.5, 1200.0",
            "1, 'M12345', 0, 0.5, 850.0",
            "1, 'M12345', 5, 0.5, 1100.0",
            "2, 'M12345', 8, 0.5, 1250.0"
    })
    public void getTempsPartielA1(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        com.ipiecoles.java.java350.model.Employe employe = new com.ipiecoles.java.java350.model.Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), com.ipiecoles.java.java350.model.Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        employe.setTempsPartiel(1d);
        Double prime = employe.getPrimeAnnuelle();

        //Then
        org.assertj.core.api.Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1d);
        Assertions.assertEquals(primeAnnuelle*2, prime);
    }

    @Test
    public void getTempsPartiel() {
        //Given
        com.ipiecoles.java.java350.model.Employe e = new com.ipiecoles.java.java350.model.Employe();
        e.setTempsPartiel(0d);

        //When
        Double tempsPartiel = e.getTempsPartiel();

        //Then
        Assertions.assertEquals(0, tempsPartiel.doubleValue());
    }
}
