package com.ipiecoles.java.java350.model;

import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {


    // Cas pourcentage positif
    @Test
    public void testAugmenterSalairePlus10() throws Exception {
        //Given
        Employe e = new Employe();
        e.setSalaire(1000.0);

        //When
        e.augmenterSalaire(10);

        //Then
        Assertions.assertThat(e.getSalaire()).isEqualTo(1100.0);
    }

    // Cas pourcentage 0
    @Test
    public void testAugmenterSalaire0() throws Exception {
        //Given
        Employe e = new Employe();
        e.setSalaire(1000.0);

        //When
        e.augmenterSalaire(0);

        //Then
        Assertions.assertThat(e.getSalaire()).isEqualTo(1000.0);
    }

    // Cas pourcentage négatif
    @Test
    public void testAugmenterSalaireMoins12(){
        //Given
        Employe e = new Employe();
        e.setSalaire(1000.0);

        //When
        Throwable exception = Assertions.catchThrowable(() -> e.augmenterSalaire(-12));

        //Then
        Assertions.assertThat(exception).isInstanceOf(Exception.class);
    }

    // Cas 2019 : l'année est non bissextile, a débuté un mardi et il y a 10 jours fériés ne tombant pas le week-end.
    // Cas 2021 : l'année est non bissextile, a débuté un vendredi et il y a 7 jours fériés ne tombant pas le week-end.
    // Cas 2022 : l'année est non bissextile, a débuté un samedi et il y a 7 jours fériés ne tombant pas le week-end.
    // Cas 2032 : l'année est bissextile, a débuté un jeudi et il y a 7 jours fériés ne tombant pas le week-end.
    @ParameterizedTest
    @CsvSource({
            "1.0, 2019-01-01, 8",
            "1.4, 2021-01-01, 16",
            "1.6, 2020-01-01, 16",
            "0.5, 2035-01-01, 5",
            "0.7, 2044-01-01, 7"
    })
    //TODO java.lang.Exception: No tests found matching Method
    //A voir tester l'autre fonction du même nom, pour le coverage
    public void testGetNbRtt(double tempsPartiel, LocalDate date, Integer resultatAttendu){
        //Given
        Employe e = new Employe();
        e.setTempsPartiel(tempsPartiel);

        //When
        int nbRtt= e.getNbRtt(date);

        //Then
        Assertions.assertThat(nbRtt).isEqualTo(resultatAttendu);
    }

}
